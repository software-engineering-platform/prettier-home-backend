package com.ph.payload.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class PropertyValueResponse {



    private Long id;

     private String value;
}
