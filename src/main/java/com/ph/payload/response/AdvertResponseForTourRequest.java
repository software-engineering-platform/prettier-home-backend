package com.ph.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ph.domain.entities.*;
import com.ph.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertResponseForTourRequest implements Serializable {


    private Long id;
    private String title;
    private Double price;
    private String location;
    private Country country;
    private City city;
    private District district;
    private List<Image> images;
}
