import asyncio
import logging
from contextlib import asynccontextmanager

from fastapi import FastAPI

from app.kafka_worker import run_kafka_worker

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("ai-service.main")

_stop_event = asyncio.Event()
_worker_task: asyncio.Task | None = None


@asynccontextmanager
async def lifespan(app: FastAPI):
    global _worker_task
    _worker_task = asyncio.create_task(run_kafka_worker(_stop_event))
    logger.info("ai-service started")
    yield
    _stop_event.set()
    if _worker_task:
        await _worker_task
    logger.info("ai-service stopped")


app = FastAPI(title="JobPortal AI Service", lifespan=lifespan)


@app.get("/health")
async def health():
    return {"status": "ok"}