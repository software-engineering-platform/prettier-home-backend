package com.ph.service;

import com.ph.domain.entities.User;
import com.ph.payload.request.UserSaveRequest;
import com.ph.payload.response.UserSaveResponse;
import com.ph.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    /**
     * @param email
     * @return Optional of User object for login process
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseEntity<?> saveUser(UserSaveRequest userSaveRequest) {
        User user = userSaveRequest.get();
        if (userRepository.existsByEmail(user.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleService.getRoles("ADMIN", "CUSTOMER"));
        User saved = userRepository.save(user);

        return ResponseEntity.ok(
                UserSaveResponse.builder()
                        .id(saved.getId())
                        .firstName(saved.getFirstName())
                        .lastName(saved.getLastName())
                        .email(saved.getEmail())
                        .phone(saved.getPhone())
                        .build()
        );
    }
}
