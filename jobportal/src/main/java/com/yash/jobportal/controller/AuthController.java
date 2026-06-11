package com.yash.jobportal.controller;

import com.yash.jobportal.dto.ApiResponse;
import com.yash.jobportal.dto.RegisterRequest;
import com.yash.jobportal.service.AuthService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse register(
            @RequestBody RegisterRequest request
    ){
        authService.register(request);

        return new ApiResponse("User registered successfully");
    }
}
