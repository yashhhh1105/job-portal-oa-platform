package com.yash.jobportal.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.jobportal.config.KafkaTopicConfig;
import com.yash.jobportal.dto.ResumeAnalyzedEvent;
import com.yash.jobportal.entity.Submission;
import com.yash.jobportal.entity.SubmissionStatus;
import com.yash.jobportal.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResumeAnalyzedListener {

    private final SubmissionRepository submissionRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = KafkaTopicConfig.RESUME_ANALYZED_TOPIC, groupId = "jobportal-core")
    public void handleResumeAnalyzed(String message) {
        ResumeAnalyzedEvent event;
        try {
            event = objectMapper.readValue(message, ResumeAnalyzedEvent.class);
        } catch (Exception e) {
            log.error("Failed to parse resume-analyzed event, dropping message: {}", message, e);
            return;
        }

        Optional<Submission> maybeSubmission = submissionRepository.findById(event.getSubmissionId());
        if (maybeSubmission.isEmpty()) {
            log.warn("Received resume-analyzed event for unknown submission id {}", event.getSubmissionId());
            return;
        }

        Submission submission = maybeSubmission.get();

        // Idempotency guard: Kafka's at-least-once delivery means this
        // listener could receive the same event twice. If we've already
        // recorded a terminal result for this submission, don't reprocess it.
        if (submission.getStatus() != SubmissionStatus.PENDING) {
            log.info("Submission {} already in status {}, ignoring duplicate event",
                    submission.getId(), submission.getStatus());
            return;
        }

        if ("FAILED".equalsIgnoreCase(event.getStatus())) {
            submission.setStatus(SubmissionStatus.FAILED);
            submission.setErrorMessage(event.getErrorMessage());
        } else {
            submission.setStatus(SubmissionStatus.DONE);
            submission.setMatchScore(event.getMatchScore());
            try {
                submission.setMatchedSkillsJson(objectMapper.writeValueAsString(event.getMatchedSkills()));
                submission.setMissingSkillsJson(objectMapper.writeValueAsString(event.getMissingSkills()));
            } catch (Exception e) {
                log.error("Failed to serialize skills lists for submission {}", submission.getId(), e);
            }
        }

        submission.setUpdatedAt(LocalDateTime.now());
        submissionRepository.save(submission);
    }
}
