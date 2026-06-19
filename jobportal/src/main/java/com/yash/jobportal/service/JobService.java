package com.yash.jobportal.service;

import com.yash.jobportal.dto.JobRequest;
import com.yash.jobportal.entity.Job;
import com.yash.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    public Job createjob(JobRequest request) {
        Job job = new Job();

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompanyName(request.getCompanyName());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());
        job.setCreatedAt(LocalDateTime.now());

        return jobRepository.save(job);
    }
}
