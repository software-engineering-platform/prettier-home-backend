package com.ph.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ph.domain.entities.Advert;
import com.ph.domain.entities.City;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DistrictResponse implements Serializable {

    private Long id;

    private String name;

    private AdvertResponseForTourRequest adverts;

    private CityResponse city;

}
