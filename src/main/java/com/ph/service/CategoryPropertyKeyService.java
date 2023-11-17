package com.ph.service;

import com.ph.domain.entities.Category;
import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.exception.customs.ConflictException;
import com.ph.exception.customs.NonDeletableException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.CategoryMapper;
import com.ph.payload.mapper.CategoryPropertyKeyMapper;
import com.ph.payload.request.CategoryPropertyKeyRequest;
import com.ph.payload.response.CategoryPropertyKeyResponse;
import com.ph.repository.CategoryPropertyKeyRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryPropertyKeyService {

    private final CategoryPropertyKeyRepository propertyKeyRepository;
    private final CategoryPropertyKeyMapper categoryPropertyKeyMapper;
    private final CategoryService categoryService;
    private final MessageUtil messageUtil;

    // Not: save() ************************************************************ C08
    /**
     * this method created for save category property key
     * @param propertyKeyRequest: represent category property key containing specific information
     * @return : ResponseEntity with created category property key
     */
    public ResponseEntity<CategoryPropertyKeyResponse> save(CategoryPropertyKeyRequest propertyKeyRequest, Long categoryId) {

        CategoryPropertyKey categoryPropertyKey = propertyKeyRequest.get();

        // get category by category id from category service
        Category category = categoryService.getCategoryById(categoryId);

        //
        List<CategoryPropertyKey> categoryPropertyKeysOfTheCategory = propertyKeyRepository.findAllPropertyKeyByCategoryId(categoryId);
        // is there same category property key in categoryPropertyKeysOfTheCategory
        for (CategoryPropertyKey key : categoryPropertyKeysOfTheCategory) {
            if(key.getName().equals(categoryPropertyKey.getName())){
                throw new ConflictException(messageUtil.getMessage("error.cpk.save.duplicate.name.in.category"));
            }
        }

        // set category to category property key
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
    public ResponseEntity<List<CategoryPropertyKeyResponse>> getPropertyKeysOfCategory(Long categoryId) {

        List<CategoryPropertyKey> propertyKeys = propertyKeyRepository.findAllPropertyKeyByCategoryId(categoryId);

        return ResponseEntity.ok(propertyKeys
                .stream()
                .map(categoryPropertyKeyMapper::mapToCategoryPropertyKeyResponse)
                .toList());
    }


    // Not: deletePropertyKey() *************************************** C10

    /**
     * This method created for delete category property key
     * @param propertyId:
     * @return deleted category property key
     */
    //Delete related records in category_property_values table
    // TODO: Check whether category property values deleted
    public ResponseEntity<CategoryPropertyKeyResponse> deletePropertyKey(Long propertyId) {

        CategoryPropertyKey categoryPropertyKey = propertyKeyRepository.findById(propertyId).orElseThrow(()
                -> new ResourceNotFoundException("Category property key not found"));

        if(categoryPropertyKey.isBuiltIn()){
            throw new ConflictException(messageUtil.getMessage("error.cpk.delete.built-in"));
        }

        propertyKeyRepository.deleteById(propertyId);
        return ResponseEntity.ok(categoryPropertyKeyMapper.mapToCategoryPropertyKeyResponse(categoryPropertyKey));

    }

    // Not: updatePropertyKey() *************************************** C09
    /**
     * This method created for update category property key
     * @param propertyId: represent category property key id
     * @param propertyKeyRequest: represent category property key request
     * @return updated category property key
     */
    public ResponseEntity<CategoryPropertyKeyResponse> updatePropertyKey(Long propertyId,
                                                                         CategoryPropertyKeyRequest propertyKeyRequest) {

        // check if category property key exists
        CategoryPropertyKey categoryPropertyKey = propertyKeyRepository.findById(propertyId).orElseThrow(()
                -> new ResourceNotFoundException(messageUtil.getMessage("error.cpk.not-found")));

        // check if category property key is built in
        if(categoryPropertyKey.isBuiltIn()){
            throw new NonDeletableException(messageUtil.getMessage("error.cpk.update.built-in"));
        }

        // update category property key
        categoryPropertyKey.setName(propertyKeyRequest.getName());

        // save category property key
        CategoryPropertyKey categoryPropertyKeyUpdated = propertyKeyRepository.save(categoryPropertyKey);

        // return updated category property key
        return ResponseEntity.ok(categoryPropertyKeyMapper.mapToCategoryPropertyKeyResponse(categoryPropertyKeyUpdated));

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
