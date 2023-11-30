package com.ph.repository;

import com.ph.domain.entities.CategoryPropertyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryPropertyKeyRepository extends JpaRepository<CategoryPropertyKey, Long> {

    boolean existsByName(String name);

    @Query("select c from CategoryPropertyKey c where c.category.id = ?1")
    List<CategoryPropertyKey> findAllPropertyKeyByCategoryId(Long categoryId);

    boolean existsBy();
}
