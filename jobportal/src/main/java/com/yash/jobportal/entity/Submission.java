package com.yash.jobportal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "resume_submissions")
@Getter
@Setter
@NoArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String candidateEmail;

    // Optional - only set when analysis is done against a specific job,
    // rather than a standalone "just score my resume" request.
    private Long jobId;

    @Column(length = 4000)
    private String resumeText;

    @Enumerated(EnumType.STRING)
    private SubmissionStatus status;

    private Double matchScore;

    // Stored as JSON array strings for now (e.g. ["Java","Spring Boot"])
    // rather than separate join tables - kept simple for this phase.
    @Column(length = 2000)
    private String matchedSkillsJson;

    @Column(length = 2000)
    private String missingSkillsJson;

    @Column(length = 1000)
    private String errorMessage;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
