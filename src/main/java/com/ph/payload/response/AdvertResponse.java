package com.ph.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ph.domain.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertResponse {


    private Long id;
    private String title;
    private String description;
    private Double price;
    private int status;
    private boolean builtIn;
    private boolean isActive;
    private int viewCount;
    private String location;
    private AdvertType advertType;
    private Country country;
    private City city;
    private District district;
    private User user;
    private Category category;
    private List<Image> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Favorite> favorites;
    private String slug;
}
