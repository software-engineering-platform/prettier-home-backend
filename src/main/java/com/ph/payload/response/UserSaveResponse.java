package com.ph.payload.response;

import com.ph.domain.entities.Role;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveResponse implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
//    private List<Role> roles;

}
