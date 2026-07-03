package com.yash.jobportal.dto;

import com.yash.jobportal.entity.Application;
import com.yash.jobportal.entity.ApplicationStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApplicationResponse {

    private final Long id;
    private final Long jobId;
    private final String jobTitle;
    private final String candidateEmail;
    private final String coverLetter;
    private final ApplicationStatus status;
    private final LocalDateTime appliedAt;

    public ApplicationResponse(Application application) {
        this.id = application.getId();
        this.jobId = application.getJob().getId();
        this.jobTitle = application.getJob().getTitle();
        this.candidateEmail = application.getCandidateEmail();
        this.coverLetter = application.getCoverLetter();
        this.status = application.getStatus();
        this.appliedAt = application.getAppliedAt();
    }
}
