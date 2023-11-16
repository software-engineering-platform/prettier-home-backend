package com.ph.payload.response;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertTypeResponse {

    private Long id;
    private String title;

}
