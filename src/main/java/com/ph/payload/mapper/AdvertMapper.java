package com.ph.payload.mapper;

import com.ph.domain.entities.Advert;
import com.ph.payload.request.AdvertRequest;
import com.ph.payload.request.AdvertRequestForUpdateByAdmin;
import com.ph.payload.request.AdvertRequestForUpdateByCustomer;
import com.ph.payload.response.AdvertResponse;
import org.springframework.stereotype.Component;

@Component
public class AdvertMapper {


  public   Advert toEntity(AdvertRequest request) {
        return Advert.builder()
                .title(request.getTitle())
                .description(request.getDesc())
                .price(request.getPrice())
                .location(request.getLocation())
                .build();
    }

    public AdvertResponse toResponse(Advert advert){
        return AdvertResponse.builder()
                .id(advert.getId())
                .createdAt(advert.getCreatedAt())
                .updatedAt(advert.getUpdatedAt())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .price(advert.getPrice())
                .status(advert.getStatus())
                .viewCount(advert.getViewCount())
                .builtIn(advert.isBuiltIn())
                .isActive(advert.isActive())
                .location(advert.getLocation())
                .country(advert.getCountry())
                .city(advert.getCity())
                .district(advert.getDistrict())
                .user(advert.getUser())
                .advertType(advert.getAdvertType())
                .category(advert.getCategory())
                  .images(advert.getImages())
                .favorites(advert.getFavorites())
                .slug(advert.getSlug())
                .build();
    }


   public Advert toEntityForUpdate(AdvertRequestForUpdateByAdmin request){
        return Advert.builder()
                .title(request.getTitle())
                .description(request.getDesc())
                .price(request.getPrice())
                .status(request.getStatus())
                .location(request.getLocation())
                .build();
    }

    public Advert toEntityForUpdateCustomer(AdvertRequestForUpdateByCustomer request){
        return Advert.builder()
                .title(request.getTitle())
                .description(request.getDesc())
                .price(request.getPrice())
                .isActive(request.isActive())
                .location(request.getLocation())
                .build();
    }




}
