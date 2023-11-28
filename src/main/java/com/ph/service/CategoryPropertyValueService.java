package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Category;
import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.domain.entities.CategoryPropertyValue;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.repository.CategoryPropertyValueRepository;
import com.ph.utils.MessageUtil;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class CategoryPropertyValueService {

    private final CategoryPropertyValueRepository repository;
    private final MessageUtil messageUtil;


    public CategoryPropertyValue saveValue(CategoryPropertyKey key, String value, Advert advert){

     CategoryPropertyValue categoryPropertyValue =   CategoryPropertyValue.builder()
                .advert(advert)
                .categoryPropertyKey(key)
                .value(value)
                .build();

       return repository.save(categoryPropertyValue);

    }

    public void updateValue(CategoryPropertyKey categoryPropertyKey, String s, Advert savedAdvert, Long aLong) {

        CategoryPropertyValue categoryPropertyValue = repository.findById(aLong).orElseThrow(() ->
                new ResourceNotFoundException(String.format(messageUtil.getMessage("error.category.property.value.not.found"), aLong)));

        categoryPropertyValue.setAdvert(savedAdvert);
        categoryPropertyValue.setCategoryPropertyKey(categoryPropertyKey);
        categoryPropertyValue.setValue(s);
        categoryPropertyValue.setId(aLong);
        repository.save(categoryPropertyValue);



    }


    public void deleteValueForNull(){
        repository.deleteByAdvertNull();
    }
}
