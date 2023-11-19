package com.ph.payload.response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryResponseForFavorite {

    private Long id;
    private String title;

}

