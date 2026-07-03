package com.yash.jobportal.controller;

import com.yash.jobportal.dto.ApplicationResponse;
import com.yash.jobportal.dto.ApplicationStatusUpdateRequest;
import com.yash.jobportal.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recruiter")
@RequiredArgsConstructor
public class RecruiterApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/jobs/{jobId}/applications")
    public List<ApplicationResponse> getApplicationsForJob(
            @PathVariable Long jobId,
            Authentication authentication
    ) {
        return applicationService.getApplicationsForJob(jobId, authentication.getName())
                .stream()
                .map(ApplicationResponse::new)
                .toList();
    }

    @PutMapping("/applications/{applicationId}/status")
    public ApplicationResponse updateStatus(
            @PathVariable Long applicationId,
            @Valid @RequestBody ApplicationStatusUpdateRequest request,
            Authentication authentication
    ) {
        return new ApplicationResponse(
                applicationService.updateApplicationStatus(
                        applicationId, request.getStatus(), authentication.getName()
                )
        );
    }
}
