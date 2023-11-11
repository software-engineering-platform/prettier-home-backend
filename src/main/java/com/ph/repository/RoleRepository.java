package com.ph.repository;

import com.ph.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByRoleName(@NonNull String roleName);
    Optional<Role> findByRoleName(@NonNull String roleName);
}
