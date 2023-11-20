package com.ph.payload.request;

import com.ph.domain.entities.Contact;
import lombok.*;

import java.io.Serializable;
import java.util.function.Supplier;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest implements Supplier<Contact>, Serializable {

    private String firstName;
    private String lastName;
    private String email;
    private String message;


    @Override
    public Contact get() {
        return Contact.builder()
                .firstName(getFirstName())
                .lastName(getLastName())
                .email(getEmail())
                .message(getMessage())
                .build();
    }
}
