package com.yash.jobportal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 2000)
    private String description;

    private String companyName;

    private String location;

    private Double salary;

    private String jobType;//FULL_TIME, PART_TIME, INTERNSHIP

    private LocalDateTime createdAt;

    //Email of the recruiter who posted this job. Used to enforce that only
    //the owning recruiter can update/delete it
    private String postedByEmail;

}
