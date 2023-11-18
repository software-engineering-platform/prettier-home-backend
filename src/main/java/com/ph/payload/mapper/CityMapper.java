package com.ph.payload.mapper;

import com.ph.domain.entities.City;
import com.ph.payload.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CityMapper {
   public  CityResponse toCityResponse(City city){
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .build();
    }
}
