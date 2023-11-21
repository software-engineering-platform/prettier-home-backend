package com.ph.payload.request;

import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.payload.request.abstracts.AdvertRequestAbs;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertRequestForUpdateByCustomer  extends AdvertRequestAbs {



    private boolean isActive;


    private List<String> propertyvalues;



}
