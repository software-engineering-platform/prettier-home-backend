package com.ph.payload.request;

import com.ph.domain.entities.User;
import lombok.*;

import java.io.Serializable;
import java.util.function.Supplier;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest implements Supplier<User>, Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private boolean builtIn;

    @Override
    public User get() {
        return User.builder()
                .firstName(getFirstName())
                .lastName(getLastName())
                .email(getEmail())
                .phone(getPhone())
                .passwordHash(getPassword())
                .builtIn(isBuiltIn())
                .build();
    }
}
