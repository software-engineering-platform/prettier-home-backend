package com.ph.service;

import com.ph.domain.entities.City;

import com.ph.exception.customs.ResourceNotFoundException;
 import com.ph.payload.mapper.LocationMapper;
import com.ph.payload.response.CityResponse;
import com.ph.repository.CityRepository;

import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final MessageUtil messageUtil;
    private final LocationMapper locationMapper;

    //Not:U02 GetAllCities() *************************************************************************
    public List<CityResponse> getAllCities() {

        return  cityRepository.findAll().stream().map(locationMapper::toCityResponse).collect(Collectors.toList());
    }
    // Not : GetById() ***************************************************************************************
    @Cacheable(value = "city", key = "#id")
    public City getById(Long id){
        return cityRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.city.not-found")));
    }
}
