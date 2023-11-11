package com.ph.payload.request;

import com.ph.domain.entities.Role;
import lombok.*;

import java.io.Serializable;
import java.util.function.Supplier;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleSaveRequest implements Supplier<Role>, Serializable {

    private String roleName;

    @Override
    public Role get() {
        return Role.builder()
                .roleName(getRoleName())
                .build();
    }
}
