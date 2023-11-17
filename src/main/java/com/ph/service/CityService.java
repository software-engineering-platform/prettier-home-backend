package com.ph.service;

import com.ph.domain.entities.City;
import com.ph.domain.entities.Country;
import com.ph.domain.entities.TourRequest;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.repository.CityRepository;
import com.ph.repository.CountriesRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final MessageUtil messageUtil;

    //Not:U02 GetAllCities() *************************************************************************
    public List<City> getAllCities() {

        return  cityRepository.findAll();
    }
    // Not : GetById() ***************************************************************************************
    public City getById(Long id){
        return cityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.city.not-found")));
    }
}
