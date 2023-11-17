package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Category;
import com.ph.exception.customs.ConflictException;
import com.ph.exception.customs.NonDeletableException;
import com.ph.exception.customs.NonUpdatableException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.CategoryMapper;
import com.ph.payload.request.CategoryRequest;
import com.ph.payload.response.CategoryResponse;
import com.ph.payload.response.CategoryWithoutPropertiesResponse;
import com.ph.repository.AdvertRepository;
import com.ph.repository.CategoryPropertyKeyRepository;
import com.ph.repository.CategoryRepository;
import com.ph.utils.MessageUtil;
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
    private final MessageUtil messageUtil;

    //NOT: saveCategory **************************************************  C04

    /**!!!
     *
     * This method create an category.
     * @param categoryRequest : represent category containing specific information
     * @return : ResponseEntity with created category
     */
    public ResponseEntity<?> save(CategoryRequest categoryRequest) {

        Category category = categoryRequest.get();
        category.setSlug(slugMaker(category.getTitle()));
        checkTitle(categoryRequest.getTitle());

        // if there is same slug in database then throw ConflictException
        checkDuplicateForSlug(category.getSlug());//NOT: is it required to check for duplicate slug???

        // save category in database
        Category saved = categoryRepository.save(category);
        // return created category
        return ResponseEntity.ok(categoryMapper.mapToCategoryResponse(saved));

    }

    public static String slugMaker(String title){
        return title.toLowerCase().replace(" ", "-")+System.currentTimeMillis();
    }


    /**
     * this method created for checking title
     * @param title : represent  title of category
     */
    public  void checkTitle( String title){

        // if the title contains character except a-zA-Z then throw ConflictException
        if(!title.matches("^[a-zA-Z ]+$")) {
            throw new ConflictException(messageUtil.getMessage("error.category.save.not-alphabetic"));
        }
        // get all Title in database
        List<String> titles = categoryRepository.findAllTitle();
        // if there is same title in database then throw ConflictException
        for(String t : titles){
            if(t.equalsIgnoreCase(title)){
                throw new ConflictException(messageUtil.getMessage("error.category.save.exist"));
            }
        }

    }

    /**
     * This method created for check duplicate slug
     * @param slug : represent customized title of category
     */
    public void checkDuplicateForSlug(String slug){
        if(categoryRepository.existsBySlug(slug)) {
            throw new ConflictException(messageUtil.getMessage("error.category.save.duplicate.slug"));
        }
    }

    //NOT: getAllCategoryWithPage *****************************************  C01

//    /**
//     * This method created for getting all categories
//     * @param page : represent page number
//     * @param size : represent page size
//     * @param sort : represent sort type
//     * @param type : represent type of sort
//     * @return : all categories with pageable type
//     */
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

    /**
     * This method created for getting all categories with list
     * @return : all categories without properties
     */
    public ResponseEntity<List<CategoryWithoutPropertiesResponse>> getAllCategory() {

        // get all categories
        List<Category> categories = categoryRepository.findAll();
        // convert categories to categoryResponses
        List<CategoryWithoutPropertiesResponse> categoryResponses = categories
                .stream()
                .map(categoryMapper::mapToCategoryResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryResponses);
    }


    //NOT: getAllCategoryWithPageAndWithAdmin **************************************************C02
    /**
     * This method created for getting all categories with page for admin and manager
     * @param page represent page number
     * @param size represent page size
     * @param sort represent sort type
     * @param type represent type of sort
     * @param query represent search query
     * @return all categories with pageable type
     */

    public Page<CategoryWithoutPropertiesResponse>
                              getAllCategoryWithPageAndWithAdmin(  int page,  int size, String sort, String type, String query) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (type.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        // if query is null or empty then return all categories
        if (query == null || query.isEmpty()) {
            return categoryRepository.findAll(pageable).map(categoryMapper::mapToCategoryResponse);
        }

        // if query is't null then return list of filtered categories which contains query
        List<CategoryWithoutPropertiesResponse> categoryResponses = categoryRepository.findAll(pageable)
                .stream()
                .filter(category -> category.getTitle().toLowerCase().contains(query.toLowerCase()))
                .map(categoryMapper::mapToCategoryResponse)
                .collect(Collectors.toList());

        // PageImpl structure : PageImpl<T>(List<T>, Pageable, int)
        // This structure takes pageable and list of categoryResponses and return data with pageable
        return new PageImpl<>(categoryResponses, pageable, categoryResponses.size());
    }


    //NOT: deleteCategory *************************************************************** C06

    /**
     * this method created for deleting the category
     * @param categoryId : represent the category id
     * @return : ResponseEntity with deleted category
     */
    public ResponseEntity<?> deleteCategory(Long categoryId) {

        //  check if category exists
        Optional<Category> category =  categoryRepository.findById(categoryId);
        if (category.isEmpty()){
            throw new ResourceNotFoundException(messageUtil.getMessage("error.category.not-found"));
        }
        // check if category is built in
        if(category.get().isBuiltIn()){ // we used get() method because of Optional
            throw new NonDeletableException(messageUtil.getMessage("error.category.delete.built-in"));
        }

        // if there is any advert related to the category then it cannot be deleted
        List<Advert> adverts = advertRepository.findByCategory_Id(categoryId);
        if(!adverts.isEmpty()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageUtil.getMessage("error.category.delete.advert"));
        }
        // delete category
        categoryRepository.deleteById(categoryId);
        // return deleted category
        return ResponseEntity.ok(categoryMapper.mapToCategoryResponse(category.get()));

    }


    //NOT: getById  ******************************************************************* CO3
    /**
     * This method created for getting category by id with category property keys
     * @param categoryId : represent category id
     * @return : ResponseEntity with category that is asked
     */
    public ResponseEntity<CategoryResponse> getById(Long categoryId) {

        // check if category exists and return
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException(messageUtil.getMessage("error.category.not-found")));

        return ResponseEntity.ok(categoryMapper.mapToCategoryResponsewithPropertyKeys(category));

    }


    //NOT: updateById ************************************************************************ C05

    /**
     * this method created for updating category
     * @param categoryId : represent category id
     * @param categoryRequest  : represent category request object
     * @return : ResponseEntity with updated category
     */

    public ResponseEntity<?> updateById(Long categoryId, CategoryRequest categoryRequest)  {
        // Check if the category with the given ID exists in the database
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            // If the category does't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(messageUtil.getMessage("error.category.not-found"));
        }

        // Get the existing category from the optional
        Category existingCategory = category.get();

        // check the category is built_in
        if(existingCategory.isBuiltIn()){
            throw new NonUpdatableException(messageUtil.getMessage("error.category.update.built-in"));
        }

        // check title for duplicate and constraint
        checkTitle(categoryRequest.getTitle());

        // Update the existing category with the new values
        existingCategory.setTitle(categoryRequest.getTitle());
        existingCategory.setIcon(categoryRequest.getIcon());
        existingCategory.setSeq(categoryRequest.getSeq());
        existingCategory.setActive(categoryRequest.isActive());
        existingCategory.setSlug(slugMaker(categoryRequest.getTitle()));

        // Save the updated category in the database
        Category saved = categoryRepository.save(existingCategory);

        // Return the updated category
        return ResponseEntity.ok(categoryMapper.mapToCategoryResponse(saved));
    }



    /**
     * this method created for getting category object for category property key service
     * @param categoryId : represent category id
     * @return : Category object
     */
    public Category getCategoryById(Long categoryId) {

        Category category =  categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.category.not-found")));
        return category;

    }



}



