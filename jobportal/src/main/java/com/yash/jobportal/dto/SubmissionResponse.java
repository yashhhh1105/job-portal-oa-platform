package com.yash.jobportal.dto;

import com.yash.jobportal.entity.Submission;
import com.yash.jobportal.entity.SubmissionStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SubmissionResponse {

    private final Long id;
    private final SubmissionStatus status;
    private final Double matchScore;
    private final String matchedSkillsJson;
    private final String missingSkillsJson;
    private final String errorMessage;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public SubmissionResponse(Submission submission) {
        this.id = submission.getId();
        this.status = submission.getStatus();
        this.matchScore = submission.getMatchScore();
        this.matchedSkillsJson = submission.getMatchedSkillsJson();
        this.missingSkillsJson = submission.getMissingSkillsJson();
        this.errorMessage = submission.getErrorMessage();
        this.createdAt = submission.getCreatedAt();
        this.updatedAt = submission.getUpdatedAt();
    }
}
