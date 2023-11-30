package com.ph.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String message;

}
