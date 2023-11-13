package com.ph.service;


import com.ph.domain.entities.User;
import com.ph.exception.customs.ConflictException;
import com.ph.payload.mapper.AuthMapper;
import com.ph.payload.request.ForgotPasswordRequest;
import com.ph.payload.request.UserSaveRequest;
import com.ph.payload.response.UserSaveResponse;
import com.ph.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;
    private final RoleService roleService;


    /**
     * Retrieve a user by their email address.
     *
     * @param email The email address of the user.
     * @return An optional User object if a user with the email address is found, otherwise an empty optional.
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves a new user to the database.
     *
     * @param userSaveRequest the request object containing user information
     * @return the response entity with the saved user information
     */
    public ResponseEntity<?> saveUser(UserSaveRequest userSaveRequest) {
        User user = userSaveRequest.get();

        // Check for duplicate user
        checkDuplicate(user);

        // Set the built-in flag to false
        user.setBuiltIn(false);

        // Hash the user's password
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));

        // Set the user's roles
        user.setRoles(roleService.getRoles("CUSTOMER"));

        // Save the user to the database
        User saved = userRepository.save(user);

        // Return the saved user information
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

    /**
     * Checks for duplicate phone number and email in the UserRepository.
     * Throws a ConflictException if a duplicate is found.
     *
     * @param user The User object to be checked for duplicates.
     */
    private void checkDuplicate(User user) {
        // Check if phone number already exists
        if (userRepository.existsByPhone(user.getPhone())) {
            throw new ConflictException("Phone number already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email already exists");
        }
    }


    /**
     * This method handles the forgot password functionality.
     * It generates a reset password code for the user,
     * saves it to the user object, and sends the code to the user's email.
     *
     * @param request The forgot password request object containing the user's email.
     * @return The HTTP response entity with status OK.
     */
    public ResponseEntity<?> forgotPassword(ForgotPasswordRequest request) {
        // Find the user by email
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        // If the user exists
        if (user != null) {
            // Generate a reset password code
            String resetPasswordCode = UUID.randomUUID().toString();
            // Save the reset password code to the user object
            user.setResetPasswordCode(resetPasswordCode);
            userRepository.save(user);
            // Send the reset password code to the user's email
            sendResetPasswordCode(user.getEmail(), resetPasswordCode);
        }
        // Return the HTTP response entity with status OK
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * This method sends the reset password code to the user's email.
     *
     * @param email             The email address of the user.
     * @param resetPasswordCode The reset password code.
     */
    private void sendResetPasswordCode(String email, String resetPasswordCode) {
        // Code to send the reset password code to the user's email
    }

    public ResponseEntity<?> getUsersPageable(String q, Pageable pageable) {
        if (q!=null){
            Page<User> pageableBySearch = userRepository.findUserPageableBySearch(q, pageable);

            //Bu kısım UserResponse nesnesine maplenerek return edilecek
            return ResponseEntity.ok(pageableBySearch);
        }
        Page<User> all = userRepository.findAll(pageable);

        //Bu kısım UserResponse nesnesine maplenerek return edilecek
        return ResponseEntity.ok(all);
    }


}
