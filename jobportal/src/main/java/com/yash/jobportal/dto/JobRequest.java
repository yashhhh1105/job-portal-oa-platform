package com.yash.jobportal.dto;

import lombok.Data;

@Data
public class JobRequest {

    private String title;
    private String description;
    private String companyName;
    private String location;
    private Double salary;
    private String jobType;
}
