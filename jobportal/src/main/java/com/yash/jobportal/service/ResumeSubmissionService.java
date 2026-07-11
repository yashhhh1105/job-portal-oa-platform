package com.yash.jobportal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.jobportal.config.KafkaTopicConfig;
import com.yash.jobportal.dto.ResumeAnalysisRequest;
import com.yash.jobportal.dto.ResumeSubmittedEvent;
import com.yash.jobportal.entity.Job;
import com.yash.jobportal.entity.Submission;
import com.yash.jobportal.entity.SubmissionStatus;
import com.yash.jobportal.exception.ResourceNotFoundException;
import com.yash.jobportal.exception.UnauthorizedException;
import com.yash.jobportal.repository.JobRepository;
import com.yash.jobportal.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeSubmissionService {

    private final SubmissionRepository submissionRepository;
    private final JobRepository jobRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public Submission submitForAnalysis(ResumeAnalysisRequest request, String candidateEmail) {
        String jobDescription = null;

        if (request.getJobId() != null) {
            Job job = jobRepository.findById(request.getJobId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Job not found with id: " + request.getJobId()));
            jobDescription = job.getDescription();
        }

        Submission submission = new Submission();
        submission.setCandidateEmail(candidateEmail);
        submission.setJobId(request.getJobId());
        submission.setResumeText(request.getResumeText());
        submission.setStatus(SubmissionStatus.PENDING);
        submission.setCreatedAt(LocalDateTime.now());
        submission.setUpdatedAt(LocalDateTime.now());
        submission = submissionRepository.save(submission);

        ResumeSubmittedEvent event = new ResumeSubmittedEvent(
                submission.getId(),
                request.getResumeText(),
                jobDescription
        );

        publishEvent(submission.getId(), event);

        return submission;
    }

    private void publishEvent(Long submissionId, ResumeSubmittedEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            // Keying by submissionId (as a string) keeps all messages for the
            // same submission on the same partition, preserving order if
            // this is ever scaled to multiple partitions later.
            kafkaTemplate.send(KafkaTopicConfig.RESUME_SUBMITTED_TOPIC, submissionId.toString(), json);
        } catch (Exception e) {
            log.error("Failed to publish resume-submitted event for submission {}", submissionId, e);
            throw new RuntimeException("Failed to submit resume for analysis. Please try again.");
        }
    }

    public Submission getSubmission(Long id, String candidateEmail) {
        Submission submission = submissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Submission not found with id: " + id));

        if (!submission.getCandidateEmail().equals(candidateEmail)) {
            throw new UnauthorizedException("You are not allowed to view this submission");
        }

        return submission;
    }
}
