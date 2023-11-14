package com.ph.payload.mapper;

import com.ph.domain.entities.Category;
import com.ph.payload.response.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse mapToCategoryResponse(Category category){
        return CategoryResponse.builder()
                        .id(category.getId())
                        .title(category.getTitle())
                        .slug(category.getSlug())
                        .icon(category.getIcon())
                        .seq(category.getSeq())
                        .builtIn(category.isBuiltIn())
                        .active(category.isActive()) // TODO:isActive olanları active yaptım
                        .build();
    }

}
