package com.ph.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertTypeRequest {

    @NotNull(message = "Title cannot be null")
    private String title;
}
