package com.yash.jobportal.controller;

import com.yash.jobportal.dto.ApplicationRequest;
import com.yash.jobportal.dto.ApplicationResponse;
import com.yash.jobportal.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidate")
@RequiredArgsConstructor
public class CandidateApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/jobs/{jobId}/apply")
    public ApplicationResponse applyToJob(
            @PathVariable Long jobId,
            @Valid @RequestBody ApplicationRequest request,
            Authentication authentication
    ) {
        return new ApplicationResponse(
                applicationService.applyToJob(jobId, authentication.getName(), request)
        );
    }

    @GetMapping("/applications")
    public List<ApplicationResponse> getMyApplications(Authentication authentication) {
        return applicationService.getMyApplications(authentication.getName())
                .stream()
                .map(ApplicationResponse::new)
                .toList();
    }
}
