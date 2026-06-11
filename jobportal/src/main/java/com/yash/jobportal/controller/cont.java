package com.yash.jobportal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class cont {

    @GetMapping("/")
    public String home() {
        return "Backend Running";
    }
}
