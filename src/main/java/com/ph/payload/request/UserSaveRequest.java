package com.ph.payload.request;

import com.ph.domain.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.function.Supplier;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest implements Supplier<User>, Serializable {

    @NotNull(message = "FirstName is required")
    private String firstName;

    @NotNull(message = "LastName is required")
    private String lastName;

    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "\\(\\d{3}\\) \\d{3}-\\d{4}", message = "Phone should be in the form of (XXX) XXX-XXXX")
    private String phone;

    @NotNull(message = "Password is required")
    @Size(min = 8, max = 30, message = "Password should be between {min} and {max} characters long")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).*$",
            message = "Password should include at least one letter, one number, and one special character")
    private String password;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be in the form of email")
    private String email;

    @Override
    public User get() {
        return User.builder()
                .firstName(getFirstName())
                .lastName(getLastName())
                .email(getEmail())
                .phone(getPhone())
                .passwordHash(getPassword())
                .build();
    }
}
