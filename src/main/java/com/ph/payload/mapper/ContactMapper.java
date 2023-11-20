package com.ph.payload.mapper;

import com.ph.domain.entities.Contact;
import com.ph.payload.response.ContactResponse;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public ContactResponse toContactResponse(Contact contact){
        return ContactResponse.builder()
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .email(contact.getEmail())
                .message(contact.getMessage())
                .build();
    }
}
