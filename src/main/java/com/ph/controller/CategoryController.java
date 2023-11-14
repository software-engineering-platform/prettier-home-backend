package com.ph.controller;

import com.ph.payload.request.CategoryRequest;
import com.ph.payload.response.CategoryResponse;
import com.ph.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    //NOT: saveCategory **************************************************
    @PostMapping() // http://localhost:8080/categories
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'CUSTOMER')")
    public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.save(categoryRequest);
    }

    //NOT: getAllCategoryWithPage **************************************************
    @GetMapping()  //http://localhost:8080/categories?page=0&size=10&sort=id&type=asc
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'CUSTOMER')")
    public Page<CategoryResponse> getAllCategoryWithPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10" ) int size,
            @RequestParam(value = "sort", defaultValue = "id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        return categoryService.getAllCategoryWithPage(page, size, sort, type);
    }

    //NOT: deleteCategory **************************************************
    @DeleteMapping("/{categoryId}") //http://localhost:8080/categories/1
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }

    //NOT: getById **************************************************
    @GetMapping("/{categoryId}") //http://localhost:8080/categories/1
    //@PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER', 'CUSTOMER')")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long categoryId){
        return categoryService.getById(categoryId);
    }

    //NOT: updateById **************************************************
    @PutMapping("/{categoryId}") //http://localhost:8080/categories/1
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<CategoryResponse> updateById(@PathVariable Long categoryId,
                                                       @Valid @RequestBody CategoryRequest categoryRequest){
        return categoryService.updateById(categoryId, categoryRequest);
    }


}
