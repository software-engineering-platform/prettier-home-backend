package com.ph.payload.mapper;

import com.ph.domain.entities.Category;
import com.ph.payload.response.CategoryResponse;
import com.ph.payload.response.CategoryWithoutPropertiesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class CategoryMapper {

   private final CategoryPropertyKeyMapper propertyKeyMapper;

    public CategoryWithoutPropertiesResponse mapToCategoryResponse(Category category){
        return CategoryWithoutPropertiesResponse.builder()
                        .id(category.getId())
                        .title(category.getTitle())
                        .slug(category.getSlug())
                        .icon(category.getIcon())
                        .seq(category.getSeq())
                        .builtIn(category.isBuiltIn())
                        .active(category.isActive()) // !!!isActive changed to active
                        .build();
    }

    public CategoryResponse mapToCategoryResponsewithPropertyKeys(Category category){
        return CategoryResponse.builder()
                        .id(category.getId())
                        .title(category.getTitle())
                        .slug(category.getSlug())
                        .icon(category.getIcon())
                        .seq(category.getSeq())
                        .builtIn(category.isBuiltIn())
                        .active(category.isActive())
                        .categoryPropertyKeysResponse(category.getCategoryPropertyKeys().stream().map(propertyKeyMapper::mapToCategoryPropertyKeyResponse).toList())
                                                        // we'l get an exception if we set cotegoryPropertyKeys object that's why we usegetCategoryPropertyKeysResponse
                        .build();
    }



}
