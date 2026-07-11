package com.yash.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Mirrors the JSON published by the FastAPI service to the
// "resume-analyzed" topic. Field names must match ai-service/app/schemas.py
// exactly - there's no shared code between the two services, only this
// agreed-upon JSON shape.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeAnalyzedEvent {
    private Long submissionId;
    private String status;       // "DONE" or "FAILED"
    private Double matchScore;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private String errorMessage; // populated only when status is "FAILED"
}
