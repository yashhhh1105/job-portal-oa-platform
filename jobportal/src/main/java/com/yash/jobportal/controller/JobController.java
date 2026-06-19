package com.yash.jobportal.controller;

import com.yash.jobportal.dto.JobRequest;
import com.yash.jobportal.entity.Job;
import com.yash.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
