package com.ph.controller;

import com.ph.domain.entities.City;
import com.ph.domain.entities.District;
import com.ph.service.CityService;
import com.ph.service.DistrictsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/districts")
public class DistrictsController {

    private final DistrictsService districtsService;

    //Not:U03 GetAllDistricts() *************************************************************************
    @GetMapping()
    public List<District> getAllDistricts() {

        return districtsService.getAllDistricts();
    }
}
