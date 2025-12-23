package com.ph.security.service;

import com.ph.domain.entities.User;
import com.ph.payload.mapper.UserMapper;
import com.ph.payload.request.LoginRequest;
import com.ph.payload.response.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void authenticate_returnsMappedResponse() {
        LoginRequest request = new LoginRequest();
        request.setEmail("user@example.com");
        request.setPassword("password1!");
        User user = new User();
        user.setEmail("user@example.com");
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null);
        when(authenticationManager.authenticate(org.mockito.Mockito.any(Authentication.class))).thenReturn(authentication);
        when(tokenService.generateToken(user)).thenReturn("token");
        LoginResponse response = LoginResponse.builder().token("token").build();
        when(userMapper.toLoginResponse(user, "token")).thenReturn(response);

        LoginResponse result = authService.authenticate(request);

        assertThat(result).isEqualTo(response);
    }
}
