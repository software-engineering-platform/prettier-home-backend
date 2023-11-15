package com.ph.service;

import com.ph.domain.entities.Category;
import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.exception.customs.ConflictException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.CategoryMapper;
import com.ph.payload.mapper.CategoryPropertyKeyMapper;
import com.ph.payload.request.CategoryPropertyKeyRequest;
import com.ph.payload.response.CategoryPropertyKeyResponse;
import com.ph.payload.response.CategoryResponse;
import com.ph.repository.CategoryPropertyKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.ConfigurationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryPropertyKeyService {

    private final CategoryPropertyKeyRepository propertyKeyRepository;
    private final CategoryPropertyKeyMapper categoryPropertyKeyMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    // NOT: save() ************************************************************ C08

    /**
     * this method created for save category property key
     * @param propertyKeyRequest: represent category property key containing specific information
     * @return : ResponseEntity with created category property key
     */
    public ResponseEntity<CategoryPropertyKeyResponse> save(CategoryPropertyKeyRequest propertyKeyRequest, Long categoryId) {

        CategoryPropertyKey categoryPropertyKey = propertyKeyRequest.get();

        // if there is same name in database then throw ConflictException
        boolean isPropertyKeyExists = propertyKeyRepository.existsByName(categoryPropertyKey.getName());
        if(isPropertyKeyExists){
           throw new ConflictException("Property key already exists");

        }
        // get category by category id from category service
        Category category= categoryService.getCategoryById(categoryId);

//         set category to category property key
        categoryPropertyKey.setCategory(category);

        // save category property key
        CategoryPropertyKey saved = propertyKeyRepository.save(categoryPropertyKey);

        return ResponseEntity.ok(categoryPropertyKeyMapper.mapToCategoryPropertyKeyResponse(saved));

    }


// Not: getPropertyKeysOfCategory() *********************************************************** C09
    /** !!!
     * This method created for getting category property keys of specific category
     * @param categoryId : represent category's id
     * @return : List of category property key
     */
    public ResponseEntity<List<CategoryPropertyKeyResponse>> getPropertyKeysOfCategory(Long categoryId){

          List<CategoryPropertyKey> propertyKeys = propertyKeyRepository.findAllPropertyKeyByCategoryId(categoryId);

       return ResponseEntity.ok(propertyKeys
                           .stream()
                           .map(categoryPropertyKeyMapper::mapToCategoryPropertyKeyResponse)
                           .toList());

    }
























    // category service icin yazildi
    // SORU: bunu klllaninca inceksin dongusu oluyor.??????????????????

    /** !!!
     * This method created for getting category property keys of specific category !!! for category service
     * @param categoryId : represent category's id
     * @return : List of category property key
     */
    public List<CategoryPropertyKey> getPropertyKeysOfCategoryForCategoryService(Long categoryId){

        return propertyKeyRepository.findAllPropertyKeyByCategoryId(categoryId);

    }




}
