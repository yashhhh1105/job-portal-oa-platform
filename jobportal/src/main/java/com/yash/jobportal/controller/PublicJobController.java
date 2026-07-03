package com.yash.jobportal.controller;

import com.yash.jobportal.entity.Job;
import com.yash.jobportal.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class PublicJobController {

    private final JobService jobService;

    @GetMapping
    public Page<Job> getAllJobs(

            @RequestParam(required = false )
            String keyword,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "createdAt")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String direction
    ) {
        return jobService.getAllJobs(
                keyword,
                page,
                size,
                sortBy,
                direction
        );
    }

    @GetMapping("/{id}")
    public Job getJobById(
            @PathVariable Long id
    ){
        return jobService.getJobById(id);
    }
}
