package com.ph.security.service;

import com.ph.security.config.TokenProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogoutServiceTest {

    @Mock
    private TokenProperties tokenProperties;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LogoutService logoutService;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void logout_clearsContextWhenHeaderMatches() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(tokenProperties.getPrefix()).thenReturn("Bearer");
        when(request.getHeader("Authorization")).thenReturn("Bearer token");

        logoutService.logout(request, response, authentication);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }

    @Test
    void logout_keepsContextWhenHeaderMissing() {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(request.getHeader("Authorization")).thenReturn(null);

        logoutService.logout(request, response, authentication);

        assertThat(SecurityContextHolder.getContext().getAuthentication()).isSameAs(authentication);
    }
}
