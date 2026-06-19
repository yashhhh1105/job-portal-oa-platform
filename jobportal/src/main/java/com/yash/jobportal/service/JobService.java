package com.yash.jobportal.service;

import com.yash.jobportal.dto.JobRequest;
import com.yash.jobportal.entity.Job;
import com.yash.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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

    public Page<Job> getAllJobs(
            String keyword,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );

        if(keyword == null || keyword.isBlank()){
            return jobRepository.findAll(pageable);
        }

        return jobRepository.findByTitleContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(
                keyword,
                keyword,
                pageable
        );
    }

    public Job getJobById(Long id){

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Job not found"));
    }

    public Job updateJob(
            Long id,
            JobRequest request
    ) {
        Job job = getJobById(id);

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompanyName(request.getCompanyName());
        job.setLocation(request.getLocation());
        job.setSalary(request.getSalary());
        job.setJobType(request.getJobType());

        return jobRepository.save(job);
    }

    public void deleteJob(Long id){
        Job job = getJobById(id);

        jobRepository.delete(job);
    }
}
