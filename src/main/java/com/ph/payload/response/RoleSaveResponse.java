package com.ph.payload.response;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleSaveResponse implements Serializable {
    private Long id;
    private String roleName;
}
