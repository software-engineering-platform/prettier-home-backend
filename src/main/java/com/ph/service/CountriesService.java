package com.ph.service;

import com.ph.domain.entities.City;
import com.ph.domain.entities.Country;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.CountryMapper;
import com.ph.payload.response.CountryResponse;
import com.ph.repository.CountriesRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountriesService {
    private final CountriesRepository countriesRepository;
    private final MessageUtil messageUtil;
    private final CountryMapper countryMapper;

    //Not:U01 GetAllCountries() *************************************************************************
    public List<CountryResponse> getAllCountries() {
        return countriesRepository.findAll().stream().map(countryMapper::toCountryResponse).collect(Collectors.toList());
    }

    // Not : GetById() ***************************************************************************************
    public Country getById(Long id) {
        return countriesRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.country.not-found")));
    }
}
