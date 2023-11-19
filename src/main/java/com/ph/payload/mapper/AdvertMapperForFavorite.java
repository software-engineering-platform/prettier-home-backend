package com.ph.payload.mapper;

import com.ph.domain.entities.Advert;
import com.ph.payload.response.AdvertResponseForFavorite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdvertMapperForFavorite {
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
}

