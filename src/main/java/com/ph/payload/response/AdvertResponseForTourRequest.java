package com.ph.payload.response;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertResponseForTourRequest implements Serializable {

    private Long id;
    private String title;
    private Double price;
    private String location;
    private CountryResponse country;
    private CityResponse city;
    private DistrictResponse district;
//    private List<Image> images;

}
