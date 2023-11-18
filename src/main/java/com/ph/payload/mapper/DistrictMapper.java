package com.ph.payload.mapper;

import com.ph.domain.entities.District;
import com.ph.payload.response.DistrictResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DistrictMapper {
  public  DistrictResponse toDistrictResponse(District district){
       return DistrictResponse.builder()
               .id(district.getId())
               .name(district.getName())
               .build();
    }
}
