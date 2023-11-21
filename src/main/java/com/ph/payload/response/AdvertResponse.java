package com.ph.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ph.domain.enums.StatusForAdvert;
import com.ph.domain.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvertResponse {


    private Long id;


    private String title;

    private String description;

    private Double price;

    private StatusForAdvert statusForAdvert;

    private boolean builtIn;

    private boolean isActive;

    private int viewCount;

    private String location;

    private AdvertTypeResponse advertType;


    private CountryResponse country;

    private CityResponse city;

    private DistrictResponse district;

    private UserResponse user;

    private CategoryResponse category;

    private List<Image> images;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Favorite> favorites;

    private List<PropertyValueResponse> categoryPropertyValue;

    private String slug;
}
