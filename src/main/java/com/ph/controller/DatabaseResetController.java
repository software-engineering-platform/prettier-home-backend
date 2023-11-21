package com.ph.controller;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Category;
import com.ph.domain.entities.User;
import com.ph.repository.AdvertRepository;
import com.ph.repository.CategoryRepository;
import com.ph.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/settings")
@RequiredArgsConstructor
public class DatabaseResetController {


    private final AdvertRepository advertRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;


    @DeleteMapping("/db-reset")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String deleteDatabase() {

        // Not: Contraint: Can not delete all builtIn objects

        // delete all not builtIn User and related objects
        List<User> users = userRepository.findAllByBuiltIn(false);
        users.forEach(userRepository::delete);

        // delete all not builtIn Advert and related objects
        List<Advert> adverts = advertRepository.findAllByBuiltIn(false);
        adverts.forEach(advertRepository::delete);

        // delete all not builtIn Category and related objects
        List<Category> categories = categoryRepository.findAllByBuiltIn(false);
        categories.forEach(categoryRepository::delete);

        return "Veritabanı sıfırlandı. Tüm öğrenci kayıtları silindi.";

    }







}
