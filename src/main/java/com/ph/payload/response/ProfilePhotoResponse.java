package com.ph.payload.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePhotoResponse {

    private Long id;
    private String name;
    private String type;
    private String data;
}
