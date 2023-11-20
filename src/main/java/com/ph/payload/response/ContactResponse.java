package com.ph.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String message;
}
