package com.ph.payload.response;

import com.ph.domain.entities.CategoryPropertyKey;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryResponse {

    private Long id;
    private String title;
    private String slug;
    private String icon;
    private int seq;
    private boolean builtIn;
    private boolean active;
    private List<CategoryPropertyKey> categoryPropertyKeys;
}
