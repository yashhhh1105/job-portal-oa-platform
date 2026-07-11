package com.yash.jobportal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResumeAnalysisRequest {

    @NotBlank(message = "Resume text is required")
    private String resumeText;

    // Optional: if analyzing against a specific job posting rather than
    // just requesting a standalone score.
    private Long jobId;
}
