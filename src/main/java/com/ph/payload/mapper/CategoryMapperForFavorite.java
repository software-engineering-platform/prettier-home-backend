package com.ph.payload.mapper;

import com.ph.domain.entities.Category;
import com.ph.payload.response.CategoryResponseForFavorite;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapperForFavorite {
   public CategoryResponseForFavorite toCategoryResponseForFavorite(Category category){
      return CategoryResponseForFavorite.builder()
               .id(category.getId())
               .title(category.getTitle())
               .build();
   }
}
