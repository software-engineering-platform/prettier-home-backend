package com.ph.controller;

import com.ph.payload.response.LoginResponse;
import com.ph.security.config.TokenProperties;
import com.ph.security.service.AuthService;
import com.ph.security.service.TokenService;
import com.ph.service.ConfirmationTokenService;
import com.ph.service.UserService;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @MockBean
    private ConfirmationTokenService confirmationTokenService;

    @MockBean
    private MessageUtil messageUtil;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private TokenProperties tokenProperties;

    @Test
    void login_returnsToken() throws Exception {
        LoginResponse response = LoginResponse.builder().token("token").build();
        when(authService.authenticate(org.mockito.Mockito.any())).thenReturn(response);

        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user@example.com\",\"password\":\"password1!\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }
}
