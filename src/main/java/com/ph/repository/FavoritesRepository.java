package com.ph.repository;

import com.ph.domain.entities.Favorite;
import com.ph.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorite, Long> {
    @Transactional
    @Modifying
    @Query("delete from Favorite f where f.advert is null")
    void deleteByAdvertNull();
    long deleteByUser(User user);
    Optional<Favorite> findByUser_IdAndAdvert_Id(Long id, Long id1);
    List<Favorite> findByUser_Id(Long id);
}
