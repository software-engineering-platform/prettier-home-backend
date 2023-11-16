package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Category;
import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.exception.customs.ConflictException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.CategoryMapper;
import com.ph.payload.request.CategoryRequest;
import com.ph.payload.response.CategoryResponse;
import com.ph.repository.AdvertRepository;
import com.ph.repository.CategoryPropertyKeyRepository;
import com.ph.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AdvertRepository advertRepository;
    private final CategoryPropertyKeyRepository categoryPropertyKeyRepository;

    //NOT: saveCategory C04

    /**!!!
     * This method created for category
     * @param categoryRequest : represent category containing specific information
     * @return : ResponseEntity with created category
     */
    public ResponseEntity<CategoryResponse> save(CategoryRequest categoryRequest) {
        Category category = categoryRequest.get();
        //  if there is same slug in database then throw ConflictException
        checkDuplicateForSlug(category.getSlug());


        String cleanedTitle = category.getTitle().toLowerCase().replaceAll("[^a-z]", "");

        if (Arrays.asList("house", "apartment", "villa", "office").contains(cleanedTitle)) {
            throw new ConflictException("The category already exists as a built_in category");
        }

        // check if category exists
        boolean isCategoryExists = categoryRepository.existsByTitle(category.getTitle());
        if(isCategoryExists){
            throw new ConflictException("Category is already exist");
        }

        Category saved = categoryRepository.save(category);
        return ResponseEntity.ok(categoryMapper.mapToCategoryResponse(saved));

    }


    /**
     * This method created for check duplicate slug
     * @param slug : represent customized title of category
     */
    public void checkDuplicateForSlug(String slug){
        if(categoryRepository.existsBySlug(slug)) {
            throw new ConflictException("Slug already exists");
        }
    }

    //NOT: getAllCategoryWithPage C01

    /**
     * This method created for getting all categories
     * @param page : represent page number
     * @param size : represent page size
     * @param sort : represent sort type
     * @param type : represent type of sort
     * @return : all categories with pageable type
     */
//    public Page<CategoryResponse> getAllCategoryWithPage(int page, int size, String sort, String type) {
//
//        Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
//        if(Objects.equals(type, "desc")) {
//            pageable = PageRequest.of(page,size,Sort.by(sort).descending());
//        }
//
//        return categoryRepository.findAll(pageable).map(categoryMapper::mapToCategoryResponse);
//    }

    //NOT: getAllCategoryWithList **************************************************C01
    public ResponseEntity<List<CategoryResponse>> getAllCategory() {

        List<Category> categories = categoryRepository.findAll();

        List<CategoryResponse> categoryResponses = categories
                .stream()
                .map(categoryMapper::mapToCategoryResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(categoryResponses);
    }

    //NOT: getAllCategoryWithPageAndWithAdmin **************************************************C02
    public Page<CategoryResponse> getAllCategoryWithPageAndWithAdmin(int page, int size, String sort, String type, String query) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (type.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        // if query is null then return all categories
        if (query == null) {
            return categoryRepository.findAll(pageable).map(categoryMapper::mapToCategoryResponse);
        }

        // if query is not null then return list of filtered categories which contains query
        List<CategoryResponse> categoryResponses = categoryRepository.findAll(pageable)
                .stream()
                .filter(category -> category.getTitle().toLowerCase().contains(query))
                .map(categoryMapper::mapToCategoryResponse)
                .collect(Collectors.toList());

        // PageImpl structure : PageImpl<T>(List<T>, Pageable, int)
        // This structure takes pageable and list of categoryResponses and return data with pageable
        return new PageImpl<>(categoryResponses, pageable, categoryResponses.size());
    }


    //NOT: deleteCategory  C06

    /**
     * this method created for deleting the category
     * @param categoryId : represent the category id
     * @return : ResponseEntity with deleted category
     */
    public ResponseEntity<?> deleteCategory(Long categoryId) {

        //  check if category exists
        Optional<Category> category =  categoryRepository.findById(categoryId);

        if (category.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        // check if category is built in
        if(category.get().isBuiltIn()){ // we used get() method because of Optional
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot delete built in category");
        }

        // TODO advert repoda conflict varsa çöz
        // TODO birlestirince kontrol et
        // if there is any advert related to the category then it cannot be deleted
        List<Advert> adverts = advertRepository.findByCategory_Id(categoryId);
        if(!adverts.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot delete category with associated adverts");
        }

        categoryRepository.deleteById(categoryId);
        return ResponseEntity.ok("Category deleted successfully");

    }


    //NOT: getById  CO3
    /**
     * This method created for getting category by id with category property keys
     * @param categoryId : represent category id
     * @return : ResponseEntity with category that is asked
     */
    public ResponseEntity<CategoryResponse> getById(Long categoryId) {

//          check if category exists and return
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException("Category not found"));


                category.getCategoryPropertyKeys().forEach(System.out::println);
        // we got all category property keys of the category
//        List<CategoryPropertyKey> categoryPropertyKeys =
//                categoryPropertyKeyRepository.findAllPropertyKeyByCategoryId(categoryId);

        // TODO: All category must have at least one category property key??????
//        if (categoryPropertyKeys.size() == 0) {
//            ResponseEntity.status(HttpStatus.FORBIDDEN).body("Category must have at least one category property key");}

//        category.setCategoryPropertyKeys(categoryPropertyKeys);

        return ResponseEntity.ok(categoryMapper.mapToCategoryResponsewithPropertyKeys(category));
    }


    //NOT: updateById  C05
    public ResponseEntity<CategoryResponse> updateById(Long categoryId, CategoryRequest categoryRequest) {

        Optional<Category> category =  categoryRepository.findById(categoryId);

        //2
        if (category.isEmpty()){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
        }

        return null;
    }


    /**
     * this method created for getting category object for category property key service
     * @param categoryId : represent category id
     * @return : Category object
     */
    public Category getCategoryById(Long categoryId) {

        Category category =  categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return category;

    }



}



