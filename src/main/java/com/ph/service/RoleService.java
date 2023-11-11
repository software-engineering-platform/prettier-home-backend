package com.ph.service;

import com.ph.domain.entities.Role;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.request.RoleSaveRequest;
import com.ph.payload.response.RoleSaveResponse;
import com.ph.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleSaveResponse saveRole(RoleSaveRequest roleRequest) {
        if (roleRepository.existsByRoleName(roleRequest.getRoleName())) return null;
        Role saved = roleRepository.save(roleRequest.get());
        return RoleSaveResponse.builder()
                .id(saved.getId())
                .roleName(saved.getRoleName())
                .build();
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public List<Role> getRoles(String... roles) {
        return Arrays.stream(roles).map(this::getOneRoleByName).collect(Collectors.toList());
    }

    private Role getOneRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Role not found: %s", roleName))
        );
    }
}
