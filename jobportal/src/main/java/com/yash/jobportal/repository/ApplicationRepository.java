package com.yash.jobportal.repository;

import com.yash.jobportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application,Long> {

    List<Application> findByCandidateEmail(String candidateEmail);

    List<Application> findByJob_Id(Long jobId);

    Optional<Application> findByJob_IdAndCandidateEmail(Long jobId, String candidateEmail);

    boolean existsByJob_IdAndCandidateEmail(Long jobId, String candidateEmail);
}
