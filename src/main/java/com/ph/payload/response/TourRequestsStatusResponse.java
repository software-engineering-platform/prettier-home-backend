package com.ph.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ph.domain.entities.Advert;
import com.ph.domain.entities.User;
import com.ph.domain.enums.Status;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TourRequestsStatusResponse {

    private Long id;

    private LocalDate tourDate;

    private LocalTime tourTime;

    private AdvertResponseForTourRequest advert;

    private UserResponse ownerUser;

    private Status status;

}
