package com.yash.jobportal.controller;

import com.yash.jobportal.dto.JobRequest;
import com.yash.jobportal.entity.Job;
import com.yash.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public Job createJob(
            @RequestBody JobRequest request
            ) {
        return jobService.createjob(request);
    }

    @PutMapping("/{id}")
    public Job updateJob(
            @PathVariable Long id,
            @RequestBody JobRequest request
    ) {
        return jobService.updateJob(
                id,
                request
        );
    }

    @DeleteMapping("/{id}")
    public String deleteJob(
            @PathVariable Long id
    ) {
        jobService.deleteJob(id);

        return "Job deleted successfully";
    }
}
