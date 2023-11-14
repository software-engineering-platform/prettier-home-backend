package com.ph.service;

import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.payload.mapper.CategoryPropertyKeyMapper;
import com.ph.payload.request.CategoryPropertyKeyRequest;
import com.ph.payload.response.CategoryPropertyKeyResponse;
import com.ph.repository.CategoryPropertyKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryPropertyKeyService {

    private final CategoryPropertyKeyRepository propertyKeyRepository;
    private final CategoryPropertyKeyMapper categoryPropertyKeyMapper;

    // NOT: save() ************************************************************C08
    public ResponseEntity<CategoryPropertyKeyResponse> save(CategoryPropertyKeyRequest propertyKeyRequest) {
        CategoryPropertyKey categoryPropertyKey = propertyKeyRequest.get();

        boolean isPropertyKeyExists = propertyKeyRepository.existsByName(categoryPropertyKey.getName());
        if(isPropertyKeyExists){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Property key already exists");
        }

        CategoryPropertyKey saved = propertyKeyRepository.save(categoryPropertyKey);
        return ResponseEntity.ok(categoryPropertyKeyMapper.mapToCategoryPropertyKeyResponse(saved));

    }
}
