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

    /**
     * This method checks if an admin user exists in the user repository.
     * If not, it creates a new admin user and saves it in the repository.
     * This method runs during application startup and ensures that the application has a built-in admin account.
     *
     * @param args The command line arguments passed to the application.
     * @throws Exception If an error occurs during the execution of the method.
     */
    @Override
    public void run(String... args) throws Exception {

        // Check if an admin user already exists in the user repository
        if (!userRepository.existsByEmail("admin@gmail.com")) {

            // Create a new admin user
            User admin = User.builder()
                    .firstName("John")
                    .lastName("Doe")
                    .email("admin@gmail.com")
                    .phone("(123) 456-7890")
                    .passwordHash(passwordEncoder.encode("admin123!"))
                    .role(Role.ADMIN)
                    .builtIn(true)
                    .build();

            // Save the admin user in the repository
            userRepository.save(admin);
        }
    }
}
