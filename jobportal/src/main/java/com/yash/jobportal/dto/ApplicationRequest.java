package com.yash.jobportal.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ApplicationRequest {

    @Size(max = 1000, message = "Cover letter must be under 1000 characters")
    private String coverLetter;
}
