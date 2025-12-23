package com.ph.security.service;

import com.ph.domain.entities.User;
import com.ph.security.config.TokenProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private TokenProperties tokenProperties;

    @InjectMocks
    private TokenService tokenService;

    @Test
    void generateAndValidateToken_roundTrip() {
        when(tokenProperties.getSecret()).thenReturn("MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDE=");
        when(tokenProperties.getValidity()).thenReturn(60000L);

        User user = new User();
        user.setEmail("user@example.com");

        String token = tokenService.generateToken(user);

        assertThat(token).isNotBlank();
        assertThat(tokenService.extractUsername(token)).isEqualTo("user@example.com");
        assertThat(tokenService.isTokenValid(token, user)).isTrue();
    }
}
