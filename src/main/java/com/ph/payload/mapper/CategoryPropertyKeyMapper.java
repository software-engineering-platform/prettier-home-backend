package com.ph.payload.mapper;

import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.payload.response.CategoryPropertyKeyResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryPropertyKeyMapper {

    public CategoryPropertyKeyResponse mapToCategoryPropertyKeyResponse(CategoryPropertyKey categoryPropertyKey){

        return CategoryPropertyKeyResponse.builder()
                .id(categoryPropertyKey.getId())
                .name(categoryPropertyKey.getName())
                .builtIn(categoryPropertyKey.isBuiltIn())
                .category(categoryPropertyKey.getCategory())
                .build();
    }

}
