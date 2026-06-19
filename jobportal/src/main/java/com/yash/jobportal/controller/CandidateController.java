package com.yash.jobportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/controller")
public class CandidateController {
    @GetMapping("/dashboard")
    public String dashboard(){
        return "Candidate Dashboard";
    }
}
