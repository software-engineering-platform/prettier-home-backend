package com.ph.payload.mapper;

import com.ph.domain.entities.Country;
import com.ph.payload.response.CountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CountryMapper {

    /*private final AdvertMapper advertMapper;
    private final CityMapper cityMapper;*/
    public  CountryResponse toCountryResponse(Country country){
        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                /*.advertsResponse(country.getAdverts().stream().map(advertMapper::toAdvertResponseForTourRequest).collect(Collectors.toList()))
                .citiesResponse(country.getCities().stream().map(cityMapper::toCityResponse).collect(Collectors.toList()))*/
                .build();
    }
}
