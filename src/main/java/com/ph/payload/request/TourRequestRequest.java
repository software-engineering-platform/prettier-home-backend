package com.ph.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ph.domain.entities.TourRequest;
import com.ph.domain.enums.Status;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Supplier;

@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TourRequestRequest implements Supplier<TourRequest>, Serializable {

    @NotNull(message = "Please enter tourdate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Future(message = "Please enter future date")
    private LocalDate tourDate;

    @NotNull(message = "Please enter tourtime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "US")
    private LocalTime tourTime;

    @NotNull(message = "Please enter advert id")
    private Long advertId;


    @Override
    public TourRequest get() {
        return TourRequest.builder()
                .tourDate(getTourDate())
                .tourTime(getTourTime())
                .status(Status.PENDING)
                .build();
    }

}
