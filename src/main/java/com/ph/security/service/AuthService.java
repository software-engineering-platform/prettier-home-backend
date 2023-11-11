package com.ph.security.service;

import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.AuthMapper;
import com.ph.payload.request.LoginRequest;
import com.ph.payload.response.LoginResponse;
import com.ph.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userService
                .getUserByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        var jwtToken = tokenService.generateToken(user);

        return authMapper.toLoginResponse(user, jwtToken);
    }

}
