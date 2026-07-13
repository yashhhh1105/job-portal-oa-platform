import asyncio
import json
import logging
import os

from aiokafka import AIOKafkaConsumer, AIOKafkaProducer

from app.analysis import analyze_resume
from app.schemas import ResumeSubmittedEvent, ResumeAnalyzedEvent

logger = logging.getLogger("ai-service.kafka_worker")

BOOTSTRAP_SERVERS = os.getenv("KAFKA_BOOTSTRAP_SERVERS", "kafka:9092")
RESUME_SUBMITTED_TOPIC = "resume-submitted"
RESUME_ANALYZED_TOPIC = "resume-analyzed"
CONSUMER_GROUP_ID = "ai-service"


async def _process_message(raw_value: bytes, producer: AIOKafkaProducer) -> None:
    try:
        payload = json.loads(raw_value.decode("utf-8"))
        event = ResumeSubmittedEvent(**payload)
    except Exception as e:
        logger.error("Failed to parse resume-submitted message, dropping: %s", e)
        return

    logger.info("Processing submission %s", event.submissionId)

    try:
        result = analyze_resume(event.resumeText, event.jobDescription)
        analyzed = ResumeAnalyzedEvent(
            submissionId=event.submissionId,
            status="DONE",
            matchScore=result["matchScore"],
            matchedSkills=result["matchedSkills"],
            missingSkills=result["missingSkills"],
        )
    except Exception as e:
        logger.exception("Analysis failed for submission %s", event.submissionId)
        analyzed = ResumeAnalyzedEvent(
            submissionId=event.submissionId,
            status="FAILED",
            errorMessage=str(e),
        )

    await producer.send_and_wait(
        RESUME_ANALYZED_TOPIC,
        key=str(analyzed.submissionId).encode("utf-8"),
        value=analyzed.model_dump_json().encode("utf-8"),
    )
    logger.info("Published result for submission %s (status=%s)", analyzed.submissionId, analyzed.status)


async def run_kafka_worker(stop_event: asyncio.Event) -> None:
    consumer = AIOKafkaConsumer(
        RESUME_SUBMITTED_TOPIC,
        bootstrap_servers=BOOTSTRAP_SERVERS,
        group_id=CONSUMER_GROUP_ID,
        auto_offset_reset="earliest",
        enable_auto_commit=True,
    )
    producer = AIOKafkaProducer(bootstrap_servers=BOOTSTRAP_SERVERS)

    await consumer.start()
    await producer.start()
    logger.info("Kafka consumer/producer started, listening on '%s'", RESUME_SUBMITTED_TOPIC)

    try:
        while not stop_event.is_set():
            try:
                # Poll with a timeout so we can check stop_event periodically
                # instead of blocking forever on getmany().
                result = await consumer.getmany(timeout_ms=1000)
                for records in result.values():
                    for record in records:
                        await _process_message(record.value, producer)
            except Exception:
                logger.exception("Error in Kafka consume loop, continuing")
                await asyncio.sleep(1)
    finally:
        await consumer.stop()
        await producer.stop()
        logger.info("Kafka consumer/producer stopped")