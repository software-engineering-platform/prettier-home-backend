package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Category;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.CategoryMapper;
import com.ph.payload.request.CategoryRequest;
import com.ph.payload.response.CategoryResponse;
import com.ph.repository.AdvertRepository;
import com.ph.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AdvertRepository advertRepository;

    //NOT: saveCategory **************************************************C04
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

    //NOT: getAllCategoryWithPage **************************************************C01
    public Page<CategoryResponse> getAllCategoryWithPage(int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
        if(Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page,size,Sort.by(sort).descending());
        }
        return categoryRepository.findAll(pageable).map(categoryMapper::mapToCategoryResponse);
    }


    //NOT: deleteCategory **************************************************C06
    public ResponseEntity<?> deleteCategory(Long categoryId) {

        //1
        // !!! check if category exists
        Optional<Category> category =  categoryRepository.findById(categoryId);

        //2
        if (category.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        //3 bu rakamları düzelttim
        // !!! check if category is built in
        if(category.get().isBuiltIn()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("SİLİNEMEZ");
        }

        // TODO advert repoda conflict varsa çöz
        // TODO birlestirince kontrol et
        // advert tablosunda ilişkili bir kayıt varsa silinemez
        // yani kategoriye ait bir ilan varsa silinemez
        List<Advert> adverts = advertRepository.findByCategory_Id(categoryId);
        if(!adverts.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot delete category with associated adverts");
        }

        categoryRepository.deleteById(categoryId);
        return ResponseEntity.ok("Category deleted successfully");

    }


    //NOT: getById *************************************************CO3
    // TODO: exceptionlar düzeltilecek
    public ResponseEntity<CategoryResponse> getById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category not found"));


        return ResponseEntity.ok(categoryMapper.mapToCategoryResponse(category));

    }

    //NOT: updateById **************************************************C05
    public ResponseEntity<CategoryResponse> updateById(Long categoryId, CategoryRequest categoryRequest) {

        Optional<Category> category =  categoryRepository.findById(categoryId);

        //2
        if (category.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        return null;
    }
}



