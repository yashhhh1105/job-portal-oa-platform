package com.yash.jobportal.controller;

import com.yash.jobportal.dto.ResumeAnalysisRequest;
import com.yash.jobportal.dto.SubmissionResponse;
import com.yash.jobportal.service.ResumeSubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate/resume")
@RequiredArgsConstructor
public class ResumeAnalysisController {

    private final ResumeSubmissionService resumeSubmissionService;

    @PostMapping("/analyze")
    public ResponseEntity<SubmissionResponse> analyze(
            @Valid @RequestBody ResumeAnalysisRequest request,
            Authentication authentication
    ) {
        var submission = resumeSubmissionService.submitForAnalysis(request, authentication.getName());
        // 202 Accepted - processing happens asynchronously, client polls the GET below
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new SubmissionResponse(submission));
    }

    @GetMapping("/analyze/{id}")
    public SubmissionResponse getResult(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return new SubmissionResponse(
                resumeSubmissionService.getSubmission(id, authentication.getName())
        );
    }
}
