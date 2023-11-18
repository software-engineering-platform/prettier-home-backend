package com.ph.payload.mapper;

import com.ph.domain.entities.Country;
import com.ph.payload.response.CountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CountryMapper {
    public  CountryResponse toCountryResponse(Country country){
        return CountryResponse.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }
}
