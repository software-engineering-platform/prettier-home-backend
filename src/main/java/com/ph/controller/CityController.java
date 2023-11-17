package com.ph.controller;

import com.ph.domain.entities.City;
import com.ph.domain.entities.Country;
import com.ph.service.CityService;
import com.ph.service.CountriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    //Not:U02 GetAllCities() *************************************************************************
    @GetMapping()
    public List<City> getAllCities() {

        return cityService.getAllCities();
    }
}
