package com.yash.jobportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class JobRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Company Name is required")
    private String companyName;

    @NotBlank(message = "Location is required")
    private String location;

    @Positive(message = "Salary must be a positive number")
    private Double salary;

    @NotBlank(message = "Job Type is required")
    private String jobType;
}
