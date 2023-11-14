package com.ph.repository;

import com.ph.domain.entities.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    //Start: categoryService için yazıldı
    //Bunu kabul etmedi kendisi asagıya olusturdu
    //List<Advert> findByCategory(Long categoryId);
    List<Advert> findByCategory_Id(Long categoryId);
    //Finish: categoryService için yazıldı

}
