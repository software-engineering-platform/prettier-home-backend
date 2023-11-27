package com.ph.payload.response;


 import com.ph.domain.enums.StatusForAdvert;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
 public class SimpleAdvertResponse {

    private Long id;

    private String title;

    private StatusForAdvert statusForAdvert;

    private Double price;

    private ImageResponse image;

    private CityResponse city;

    private DistrictResponse district;

    private String slug;





}
