package com.yash.jobportal.controller;

import com.yash.jobportal.dto.ApiResponse;
import com.yash.jobportal.dto.RegisterRequest;
import com.yash.jobportal.dto.LoginRequest;
import com.yash.jobportal.dto.LoginResponse;
import com.yash.jobportal.service.AuthService;
import jakarta.validation.Valid;
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
            @Valid @RequestBody RegisterRequest request
    ){
        authService.register(request);

        return new ApiResponse("User registered successfully");
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ){
        String token = authService.login(request);

        return new LoginResponse(token);
    }
}
