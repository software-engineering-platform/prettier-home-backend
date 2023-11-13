package com.ph.repository;

import com.ph.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
            select u from User u
            where upper(u.firstName) like upper(concat('%', ?1, '%')) or upper(u.lastName) like upper(concat('%', ?1, '%')) or upper(u.email) like upper(concat('%', ?1, '%')) or upper(u.phone) like upper(concat('%', ?1, '%'))""")
    Page<User> findUserPageableBySearch(@Nullable String q, Pageable pageable);
    boolean existsByPhone(@NonNull String phone);
    boolean existsByEmail(@NonNull String email);
    Optional<User> findByEmail(String email);
}
