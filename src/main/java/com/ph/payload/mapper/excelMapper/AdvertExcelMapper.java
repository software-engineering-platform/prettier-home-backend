package com.ph.payload.mapper.excelMapper;

import com.ph.domain.entities.Advert;
import com.ph.domain.enums.StatusForAdvert;
import com.ph.payload.resource.AdvertResource;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AdvertExcelMapper {


    public AdvertResource mapToResource(Advert advert) {

        return AdvertResource.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .price(advert.getPrice())
                .statusForAdvert(advert.getStatusForAdvert())
                .builtIn(advert.isBuiltIn())
                .isActive(advert.isActive())
                .viewCount(advert.getViewCount())
                .advertType(advert.getAdvertType().getTitle())
                .countryName(advert.getCountry().getName())
                .cityName(advert.getCity().getName())
                .districtName(advert.getDistrict().getName())
                .userName(advert.getUser().getFirstName()+" "+advert.getUser().getLastName())
                .createdAt(advert.getCreatedAt())
                .build();

    }



}
