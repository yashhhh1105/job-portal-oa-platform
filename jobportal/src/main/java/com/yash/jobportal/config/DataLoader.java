package com.yash.jobportal.config;

import com.yash.jobportal.entity.Role;
import com.yash.jobportal.entity.User;
import com.yash.jobportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String[] args) {

        // Admin User
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(
                    passwordEncoder.encode("admin123")
            );
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("Admin user created");
        }

        // Recruiter User
        if (userRepository.findByEmail("recruiter@gmail.com").isEmpty()) {

            User recruiter = new User();
            recruiter.setName("Recruiter");
            recruiter.setEmail("recruiter@gmail.com");
            recruiter.setPassword(
                    passwordEncoder.encode("recruiter123")
            );
            recruiter.setRole(Role.RECRUITER);

            userRepository.save(recruiter);

            System.out.println("Recruiter user created");
        }

    }
}
