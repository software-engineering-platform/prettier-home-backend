package com.ph.payload.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertResponseForFavorite implements Serializable {

    private Long id;
    private String title;
    private Double price;
    private String location;
    private AdvertTypeResponse advertType;
    private CountryResponse country;
    private CityResponse city;
    private DistrictResponse district;
    private CategoryResponseForFavorite category;
//    private List<Image> images;

}
