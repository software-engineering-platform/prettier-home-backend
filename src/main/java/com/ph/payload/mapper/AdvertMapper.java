package com.ph.payload.mapper;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.AdvertType;
import com.ph.domain.enums.StatusForAdvert;
import com.ph.payload.request.AdvertRequest;
import com.ph.payload.request.AdvertRequestForUpdateByAdmin;
import com.ph.payload.request.AdvertRequestForUpdateByCustomer;
import com.ph.payload.request.AdvertTypeRequest;
import com.ph.payload.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final LocationMapper locationMapper;
    private final ImageMapper imageMapper;


    public Advert toEntity(AdvertRequest request) {
        return Advert.builder()
                .title(request.getTitle())
                .description(request.getDesc())
                .price(request.getPrice())
                .location(request.getLocation())
                .statusForAdvert(StatusForAdvert.PENDING)
                .viewCount(0)
                .builtIn(false)
                .isActive(true)
                .build();
    }

    public DetailedAdvertResponse toDetailedAdvertResponse(Advert advert) {
        return DetailedAdvertResponse.builder()
                .id(advert.getId())
                .createdAt(advert.getCreatedAt())
                .updatedAt(advert.getUpdatedAt())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .categoryPropertyValue(categoryMapper.entityToResponse(advert.getCategoryPropertyValues()))
                .price(advert.getPrice())
                .statusForAdvert(advert.getStatusForAdvert())
                .advertType(toResponse(advert.getAdvertType()))
                .viewCount(advert.getViewCount())
                .builtIn(advert.isBuiltIn())
                .isActive(advert.isActive())
                .country(locationMapper.toCountryResponse(advert.getCountry()))
                .city(locationMapper.toCityResponse(advert.getCity()))
                .district(locationMapper.toDistrictResponse(advert.getDistrict()))
                .images(advert.getImages().stream().map(imageMapper::toImageResponse).toList())
                .location(advert.getLocation())
                .user(userMapper.toUserResponse(advert.getUser()))
                .category(categoryMapper.mapToCategoryResponsewithPropertyKeys(advert.getCategory()))
                .slug(advert.getSlug())
                .build();
    }

    public DetailedAdvertResponse toResponseForSave(Advert advert) {
        return DetailedAdvertResponse.builder()
                .id(advert.getId())
                .createdAt(advert.getCreatedAt())
                .updatedAt(advert.getUpdatedAt())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .price(advert.getPrice())
                .statusForAdvert(advert.getStatusForAdvert())
                .advertType(toResponse(advert.getAdvertType()))
                .viewCount(advert.getViewCount())
                .builtIn(advert.isBuiltIn())
                .isActive(advert.isActive())
                .country(locationMapper.toCountryResponse(advert.getCountry()))
                .city(locationMapper.toCityResponse(advert.getCity()))
                .district(locationMapper.toDistrictResponse(advert.getDistrict()))
//                .images(advert.getImages())
                .location(advert.getLocation())
                .user(userMapper.toUserResponse(advert.getUser()))
                .category(categoryMapper.mapToCategoryResponsewithPropertyKeys(advert.getCategory()))
                .slug(advert.getSlug())
                .build();
    }


    public void toEntityForUpdate(Advert pojo, AdvertRequestForUpdateByAdmin request) {
        pojo.setTitle(request.getTitle());
        pojo.setDescription(request.getDesc());
        pojo.setPrice(request.getPrice());
        pojo.setLocation(request.getLocation());
        pojo.setStatusForAdvert(request.getStatusForAdvert());
    }

    public void toEntityForUpdateCustomer(Advert pojo, AdvertRequestForUpdateByCustomer request) {

        pojo.setTitle(request.getTitle());
        pojo.setDescription(request.getDesc());
        pojo.setPrice(request.getPrice());
        pojo.setActive(request.isActive());
        pojo.setLocation(request.getLocation());

    }


    public SimpleAdvertResponse toSimpleAdvertResponse(Advert advert) {
        return SimpleAdvertResponse.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .price(advert.getPrice())
                .statusForAdvert(advert.getStatusForAdvert())
                .city(locationMapper.toCityResponse(advert.getCity()))
                .district(locationMapper.toDistrictResponse(advert.getDistrict()))
                .country(locationMapper.toCountryResponse(advert.getCountry()))
                .image(imageMapper.toImageResponse(advert.getImages().get(0)))
                .slug(advert.getSlug())
                .build();
    }

    //NOT ADVERT TYPE

    public AdvertTypeResponse toResponse(AdvertType advertType) {
        return AdvertTypeResponse.builder()
                .id(advertType.getId())
                .title(advertType.getTitle())
                .build();
    }

    public AdvertType toEntity(AdvertTypeRequest request) {
        return AdvertType.builder()
                .title(request.getTitle())
                .build();
    }

    //NOT ADVERT TYPE


    //NOT FAVOURITE

    public AdvertResponseForFavorite toAdvertResponseForFavorite(Advert advert) {
        return AdvertResponseForFavorite.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .price(advert.getPrice())
                .location(advert.getLocation())
                .advertType(toResponse(advert.getAdvertType()))
                .country(locationMapper.toCountryResponse(advert.getCountry()))
                .city(locationMapper.toCityResponse(advert.getCity()))
                .district(locationMapper.toDistrictResponse(advert.getDistrict()))
                .category(categoryMapper.toCategoryResponseForFavorite(advert.getCategory()))
                .images(advert.getImages().stream().map(imageMapper::toImageResponse).toList())
                .slug(advert.getSlug())
                .build();
    }


    //NOT TOURREQUEST

    public AdvertResponseForTourRequest toAdvertResponseForTourRequest(Advert advert) {
        return AdvertResponseForTourRequest.builder()
                .id(advert.getId())
                .price(advert.getPrice())
                .title(advert.getTitle())
                .city(locationMapper.toCityResponse(advert.getCity()))
                /* .images(advert.getImages())*/
                .location(advert.getLocation())
                .district(locationMapper.toDistrictResponse(advert.getDistrict()))
                .country(locationMapper.toCountryResponse(advert.getCountry()))
                .build();
    }


}
