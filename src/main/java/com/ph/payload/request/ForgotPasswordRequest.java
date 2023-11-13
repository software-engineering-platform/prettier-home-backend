package com.ph.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be a valid email address")
    private String email;

}
