package com.ph.payload.mapper;

import com.ph.domain.entities.Advert;
import com.ph.domain.enums.StatusForAdvert;
import com.ph.payload.request.AdvertRequest;
import com.ph.payload.request.AdvertRequestForUpdateByAdmin;
import com.ph.payload.request.AdvertRequestForUpdateByCustomer;
import com.ph.payload.response.AdvertResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertMapper {
    private final CategoryMapper mapper;
    private final UserMapper userMapper;
    private final AdvertTypeMapper advertTypeMapper;
     private final CountryMapper countryMapper;
    private final CityMapper cityMapper;
    private final DistrictMapper districtMapper;
    private final PropertyValueMapper propertyValueMapper;
//    private final ImageMapper imageMapper;

    public   Advert toEntity(AdvertRequest request) {
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

    public AdvertResponse toResponse(Advert advert){
        return AdvertResponse.builder()
                .id(advert.getId())
                .createdAt(advert.getCreatedAt())
                .updatedAt(advert.getUpdatedAt())
                .title(advert.getTitle())
                .description(advert.getDescription())
                .categoryPropertyValue(propertyValueMapper.entityToResponse(advert.getCategoryPropertyValues()))
                .price(advert.getPrice())
                .statusForAdvert(advert.getStatusForAdvert())
                .advertType(advertTypeMapper.toResponse(advert.getAdvertType()))
                .viewCount(advert.getViewCount())
                .builtIn(advert.isBuiltIn())
                .isActive(advert.isActive())
                .country(countryMapper.toCountryResponse(advert.getCountry()))
                .city(cityMapper.toCityResponse(advert.getCity()))
                .district(districtMapper.toDistrictResponse(advert.getDistrict()))
//                .images(advert.getImages())
                .location(advert.getLocation())
                .user(userMapper.toUserResponse(advert.getUser()))
                .category(mapper.mapToCategoryResponsewithPropertyKeys(advert.getCategory()))
                .slug(advert.getSlug())
                .build();
    }
    public AdvertResponse toResponseForSave(Advert advert){
        return AdvertResponse.builder()
                .id(advert.getId())
                .createdAt(advert.getCreatedAt())
                .updatedAt(advert.getUpdatedAt())
                .title(advert.getTitle())
                .description(advert.getDescription())
                 .price(advert.getPrice())
                .statusForAdvert(advert.getStatusForAdvert())
                .advertType(advertTypeMapper.toResponse(advert.getAdvertType()))
                .viewCount(advert.getViewCount())
                .builtIn(advert.isBuiltIn())
                .isActive(advert.isActive())
                .country(countryMapper.toCountryResponse(advert.getCountry()))
                .city(cityMapper.toCityResponse(advert.getCity()))
                .district(districtMapper.toDistrictResponse(advert.getDistrict()))
//                .images(advert.getImages())
                .location(advert.getLocation())
                .user(userMapper.toUserResponse(advert.getUser()))
                .category(mapper.mapToCategoryResponsewithPropertyKeys(advert.getCategory()))
                .slug(advert.getSlug())
                .build();
    }


    public void toEntityForUpdate(Advert pojo,AdvertRequestForUpdateByAdmin request){
        pojo.setTitle(request.getTitle());
        pojo.setDescription(request.getDesc());
        pojo.setPrice(request.getPrice());
        pojo.setLocation(request.getLocation());
        pojo.setStatusForAdvert(request.getStatusForAdvert());

    }

    public void toEntityForUpdateCustomer(Advert pojo,AdvertRequestForUpdateByCustomer request){

        pojo.setTitle(request.getTitle());
        pojo.setDescription(request.getDesc());
        pojo.setPrice(request.getPrice());
        pojo.setActive(request.isActive());
        pojo.setLocation(request.getLocation());

    }




}
