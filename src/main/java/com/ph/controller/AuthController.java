package com.ph.controller;

import com.ph.payload.request.LoginRequest;
import com.ph.payload.request.UserSaveRequest;
import com.ph.payload.response.LoginResponse;
import com.ph.security.service.AuthService;
import com.ph.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<?> userSave(@RequestBody @Valid UserSaveRequest request) {
        return userService.saveUser(request);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }
}
