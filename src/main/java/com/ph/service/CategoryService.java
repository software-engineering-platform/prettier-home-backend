package com.ph.service;

import com.ph.domain.entities.Category;
import com.ph.payload.mapper.CategoryMapper;
import com.ph.payload.request.CategoryRequest;
import com.ph.payload.response.CategoryResponse;
import com.ph.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    //NOT: saveCategory **************************************************
    public ResponseEntity<CategoryResponse> save(CategoryRequest categoryRequest) {
        Category category = categoryRequest.get();
        // !!! if there is same slug in database then throw ConflictException
        checkDuplicateForSlug(category.getSlug());
        // !!! check if category exists
        boolean isCategoryExists = categoryRepository.existsByTitle(category.getTitle());
        if(isCategoryExists){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Category already exists");
        }
        Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(categoryMapper.mapToCategoryResponse(saved));

    }

    // !!! this method created for category servise
    public void checkDuplicateForSlug(String slug){
        if(categoryRepository.existsBySlug(slug)) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("Slug already exists");
        }
    }

}
