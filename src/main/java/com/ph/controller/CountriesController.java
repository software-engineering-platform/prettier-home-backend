package com.ph.controller;

import com.ph.domain.entities.Country;
import com.ph.service.CountriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountriesController {

    private final CountriesService countriesService;

    //Not:U01 GetAllCountries() *************************************************************************
    @GetMapping()
    public List<Country> getAllCountries() {
        return countriesService.getAllCountries();
    }
}
