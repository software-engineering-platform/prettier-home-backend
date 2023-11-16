package com.ph;

import com.ph.domain.entities.User;
import com.ph.repository.UserRepository;
import com.ph.security.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@SpringBootApplication
public class PrettierhomeApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PrettierhomeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        if (!userRepository.existsByEmail("admin@gmail.com")) {
            User admin = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("admin@gmail.com")
                    .phone("(123) 456-7890")
                    .passwordHash(passwordEncoder.encode("admin123!"))
                    .role(Role.ADMIN)
                    .builtIn(true)
                    .build();
            userRepository.save(admin);
        }

    }
}
