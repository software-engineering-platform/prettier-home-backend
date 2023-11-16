package com.ph.payload.request;

import com.ph.payload.request.abstracts.AdvertRequestAbs;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertRequestForUpdateByAdmin extends AdvertRequestAbs {



    private Integer status;




}
