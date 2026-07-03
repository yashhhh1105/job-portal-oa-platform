package com.yash.jobportal.service;

import com.yash.jobportal.dto.ApplicationRequest;
import com.yash.jobportal.entity.Application;
import com.yash.jobportal.entity.ApplicationStatus;
import com.yash.jobportal.entity.Job;
import com.yash.jobportal.exception.DuplicateApplicationException;
import com.yash.jobportal.exception.ResourceNotFoundException;
import com.yash.jobportal.exception.UnauthorizedException;
import com.yash.jobportal.repository.ApplicationRepository;
import com.yash.jobportal.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public Application applyToJob(Long jobId, String candidateEmail, ApplicationRequest request) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (applicationRepository.existsByJob_IdAndCandidateEmail(jobId, candidateEmail)) {
            throw new DuplicateApplicationException("You have already applied to this job");
        }

        Application application = new Application();
        application.setJob(job);
        application.setCandidateEmail(candidateEmail);
        application.setCoverLetter(request.getCoverLetter());
        application.setStatus(ApplicationStatus.APPLIED);
        application.setAppliedAt(LocalDateTime.now());

        return applicationRepository.save(application);
    }

    public List<Application> getMyApplications(String candidateEmail) {
        return applicationRepository.findByCandidateEmail(candidateEmail);
    }

    public List<Application> getApplicationsForJob(Long jobId, String recruiterEmail) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!job.getPostedByEmail().equals(recruiterEmail)) {
            throw new UnauthorizedException("You are not allowed to view applicants for this job");
        }

        return applicationRepository.findByJob_Id(jobId);
    }

    public Application updateApplicationStatus(Long applicationId, ApplicationStatus status, String recruiterEmail) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        if (!application.getJob().getPostedByEmail().equals(recruiterEmail)) {
            throw new UnauthorizedException("You are not allowed to update this application");
        }

        application.setStatus(status);
        return applicationRepository.save(application);
    }
}
