package com.ph.controller;

import com.ph.payload.request.ForgotPasswordRequest;
import com.ph.payload.request.LoginRequest;
import com.ph.payload.request.UserSaveRequest;
import com.ph.payload.response.LoginResponse;
import com.ph.security.service.AuthService;
import com.ph.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<?> userSave(@RequestBody @Valid UserSaveRequest request) {
        return userService.saveUser(request);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticate(loginRequest));
    }

    @PostMapping("forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        return userService.forgotPassword(request);
    }

    @GetMapping("admin")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<?> getUsersPageable(
            @RequestParam(name = "q", required = false) String q,
            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return userService.getUsersPageable(q, pageable);
    }

}
