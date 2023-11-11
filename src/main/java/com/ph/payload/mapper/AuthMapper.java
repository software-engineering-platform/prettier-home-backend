package com.ph.payload.mapper;

import com.ph.domain.entities.Role;
import com.ph.domain.entities.User;
import com.ph.payload.response.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public LoginResponse toLoginResponse(User user, String token) {
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRoles().stream().map(Role::getRoleName).toList())
                .build();
    }
}
