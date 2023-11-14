package com.ph.payload.response;

import com.ph.domain.entities.Category;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryPropertyKeyResponse {
    private Long id;
    private String name;
    private Boolean builtIn;
    private Category category;
}
