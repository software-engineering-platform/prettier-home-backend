package com.ph.service;

import com.ph.domain.entities.City;
import com.ph.domain.entities.Country;
import com.ph.domain.entities.TourRequest;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.CityMapper;
import com.ph.payload.response.CityResponse;
import com.ph.repository.CityRepository;
import com.ph.repository.CountriesRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final MessageUtil messageUtil;
    private final CityMapper cityMapper;

    //Not:U02 GetAllCities() *************************************************************************
    public List<CityResponse> getAllCities() {

        return  cityRepository.findAll().stream().map(cityMapper::toCityResponse).collect(Collectors.toList());
    }
    // Not : GetById() ***************************************************************************************
    public City getById(Long id){
        return cityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.city.not-found")));
    }
}
