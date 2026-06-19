package com.yash.jobportal.service;

import com.yash.jobportal.dto.RegisterRequest;
import com.yash.jobportal.dto.LoginRequest;
import com.yash.jobportal.entity.Role;
import com.yash.jobportal.entity.User;
import com.yash.jobportal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                        JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtService= jwtService;
    }

    public void register(RegisterRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new RuntimeException(("Email already exists"));
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(Role.CANDIDATE);

        userRepository.save(user);
    }

    public String login(LoginRequest request){
        User user = userRepository
                .findByEmail(request.getEmail()).orElseThrow(()->
                        new RuntimeException("User not found"));

        boolean matches = passwordEncoder.matches(
                request.getPassword(), user.getPassword()
        );

        if(!matches){
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(user.getEmail());
    }
}
