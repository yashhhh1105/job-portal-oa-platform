package com.yash.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeSubmittedEvent {
    private Long submissionId;
    private String resumeText;
    private String jobDescription; // null if this is a standalone analysis request
}
