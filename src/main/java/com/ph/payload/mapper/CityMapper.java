package com.ph.payload.mapper;

import com.ph.domain.entities.City;
import com.ph.payload.response.CityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CityMapper {

    /*private final AdvertMapper advertMapper;*/
    /*private final CountryMapper countryMapper;
    private final DistrictMapper districtMapper;*/

   public  CityResponse toCityResponse(City city){
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                /*.countryResponse(countryMapper.toCountryResponse(city.getCountry()))
                .advertsResponse(city.getAdverts().stream().map(advertMapper::toAdvertResponseForTourRequest).collect(Collectors.toList()))
                .districtsResponse(city.getDistricts().stream().map(districtMapper::toDistrictResponse).collect(Collectors.toList()))*/
                .build();
    }
}
