package com.ph.payload.response;

import com.ph.domain.entities.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertResponseForFavorite {
    private Long id;
    private String title;
    private Double price;
    private String location;
    private AdvertTypeResponse advertType;
    private CountryResponse country;
    private CityResponse city;
    private DistrictResponse district;
    private CategoryResponseForFavorite category;
    /*private List<Image> images;*/
}
