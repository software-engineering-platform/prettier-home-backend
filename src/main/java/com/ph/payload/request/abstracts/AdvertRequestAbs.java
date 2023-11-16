package com.ph.payload.request.abstracts;

import jakarta.annotation.Nullable;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AdvertRequestAbs implements Serializable {



    @NotNull(message = "Title cannot be null")
    @Size(min=5, max=150, message = "Your username should be at least 5 chars")
    private String title;

    @Nullable
    @Size( max=300, message = "Your description should be at max  300 chars")
    private String desc;


    private String location;


    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotNull(message = "Advert Type ID cannot be null")
    private Long advertTypeId;

    @NotNull(message = "Country ID cannot be null")
    private Long countryId;

    @NotNull(message = "City ID cannot be null")
    private Long cityId;

    @NotNull(message = "District ID cannot be null")
    private Long districtId;

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;




}
