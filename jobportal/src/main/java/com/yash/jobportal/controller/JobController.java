package com.yash.jobportal.controller;

import com.yash.jobportal.dto.JobRequest;
import com.yash.jobportal.entity.Job;
import com.yash.jobportal.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public Job createJob( @Valid
            @RequestBody JobRequest request,
                          Authentication authentication
            ) {
        return jobService.createJob(request, authentication.getName());
    }

    @PutMapping("/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequest request,
            Authentication authentication
    ) {
        return jobService.updateJob(
                id,
                request,
                authentication.getName()
        );
    }

    @PutMapping("/{id}/close")
    public Job closeJob(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return jobService.closeJob(id, authentication.getName());
    }

    @PutMapping("/{id}/reopen")
    public Job reopenJob(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return jobService.reopenJob(id, authentication.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteJob(
            @PathVariable Long id,
            Authentication authentication
    ) {
        jobService.deleteJob(id, authentication.getName());

        return "Job deleted successfully";
    }
}
