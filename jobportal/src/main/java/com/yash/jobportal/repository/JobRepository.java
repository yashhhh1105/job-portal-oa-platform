package com.yash.jobportal.repository;

import com.yash.jobportal.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor {

    Page<Job> findByTitleContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(String title,
                                                                               String companyName,
                                                                               Pageable pageable
    );
}
