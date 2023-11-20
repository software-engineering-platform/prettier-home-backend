package com.ph.payload.mapper;

import com.ph.domain.entities.Advert;
import com.ph.payload.response.AdvertResponseForFavorite;
import com.ph.payload.response.AdvertResponseForTourRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertMapperForFavoriteAndTourRequest {
    private final AdvertTypeMapper advertTypeMapper;
    private final CountryMapper countryMapper;
    private final CityMapper cityMapper;
    private final DistrictMapper districtMapper;
    private final CategoryMapperForFavorite categoryMapperForFavorite;

    public AdvertResponseForFavorite toAdvertResponseForFavorite(Advert advert){
        return AdvertResponseForFavorite.builder()
                .id(advert.getId())
                .title(advert.getTitle())
                .price(advert.getPrice())
                .location(advert.getLocation())
                .advertType(advertTypeMapper.toResponse(advert.getAdvertType()))
                .country(countryMapper.toCountryResponse(advert.getCountry()))
                .city(cityMapper.toCityResponse(advert.getCity()))
                .district(districtMapper.toDistrictResponse(advert.getDistrict()))
                .category(categoryMapperForFavorite.toCategoryResponseForFavorite(advert.getCategory()))
                .build();
    }
    public AdvertResponseForTourRequest toAdvertResponseForTourRequest(Advert advert){
        return AdvertResponseForTourRequest.builder()
                .id(advert.getId())
                .price(advert.getPrice())
                .title(advert.getTitle())
                .city(cityMapper.toCityResponse(advert.getCity()))
                /* .images(advert.getImages())*/
                .location(advert.getLocation())
                .district(districtMapper.toDistrictResponse(advert.getDistrict()))
                .country(countryMapper.toCountryResponse(advert.getCountry()))
                .build();
    }
}

