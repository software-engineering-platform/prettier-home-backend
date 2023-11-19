package com.ph.payload.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryWithoutPropertiesResponse {


    private Long id;
    private String title;
    private String slug;
    private String icon;
    private int seq;
    private boolean builtIn;
    private boolean active;

}
