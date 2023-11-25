package com.ph.payload.mapper;

import com.ph.domain.entities.Contact;
import com.ph.domain.entities.User;
import com.ph.payload.response.ContactResponse;
import com.ph.payload.response.LoginResponse;
import com.ph.payload.response.UserResponse;
import com.ph.payload.response.UserSaveResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public LoginResponse toLoginResponse(User user, String token) {
        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole().name())
                .build();
    }

    public UserSaveResponse toUserSaveResponse(User user) {
        return UserSaveResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }


    public ContactResponse toContactResponse(Contact contact){
        return ContactResponse.builder()
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .build();
    }

}
