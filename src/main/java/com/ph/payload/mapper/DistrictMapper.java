package com.ph.payload.mapper;

import com.ph.domain.entities.District;
import com.ph.payload.response.DistrictResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DistrictMapper {
    /*private final AdvertMapper advertMapper;
    private final CityMapper cityMapper;*/
  public  DistrictResponse toDistrictResponse(District district){
       return DistrictResponse.builder()
               .id(district.getId())
               .name(district.getName())
               /*.advertsResponse(district.getAdverts().stream().map(advertMapper::toAdvertResponseForTourRequest).collect(Collectors.toList()))
               .cityResponse(cityMapper.toCityResponse(district.getCity()))*/
               .build();
    }
}
