package com.ph.controller;

import com.ph.payload.request.CategoryPropertyKeyRequest;
import com.ph.payload.response.CategoryPropertyKeyResponse;
import com.ph.service.CategoryPropertyKeyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPropertyKeyController {

    private final CategoryPropertyKeyService propertyKeyService;

    // NOT: save() ************************************************************C08
    @PostMapping("/{categoryId}/properties") // http://localhost:8080/categories/1/properties
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<CategoryPropertyKeyResponse> save(@Valid @RequestBody
                                                            CategoryPropertyKeyRequest propertyKeyRequest) {
        return propertyKeyService.save(propertyKeyRequest);
    }

}
