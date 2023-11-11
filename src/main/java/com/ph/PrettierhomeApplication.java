package com.ph;

import com.ph.payload.request.RoleSaveRequest;
import com.ph.payload.request.UserSaveRequest;
import com.ph.service.RoleService;
import com.ph.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class PrettierhomeApplication implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;

    public static void main(String[] args) {
        SpringApplication.run(PrettierhomeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String[] roles = {"ADMIN", "MANGER", "CUSTOMER"};
        for (String role : roles) {
            roleService.saveRole(
                    RoleSaveRequest.builder()
                            .roleName(role)
                            .build()
            );
        }
        UserSaveRequest admin = UserSaveRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("admin@gmail.com")
                .phone("1234567890")
                .password("admin123")
                .builtIn(true)
                .build();
        userService.saveUser(admin);

        // Built-in admin check and create
        // First check if admin exists
        // If not, create
        // For create admin, first create UserSaveRequest
        // Then call save method on UserService
    }
}
