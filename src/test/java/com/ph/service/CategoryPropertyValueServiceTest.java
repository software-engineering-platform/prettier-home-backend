package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.domain.entities.CategoryPropertyValue;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.repository.CategoryPropertyValueRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryPropertyValueServiceTest {

    @Mock
    private CategoryPropertyValueRepository repository;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private CategoryPropertyValueService service;

    @Test
    void updateValue_throwsWhenMissing() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("error.category.property.value.not.found")).thenReturn("not found");

        assertThatThrownBy(() -> service.updateValue("new", 1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void deleteValue_deletesAllIds() {
        CategoryPropertyValue v1 = CategoryPropertyValue.builder().id(1L).build();
        CategoryPropertyValue v2 = CategoryPropertyValue.builder().id(2L).build();

        service.deleteValue(List.of(v1, v2));

        verify(repository).deleteAllById(List.of(1L, 2L));
    }

    @Test
    void saveValue_persistsEntity() {
        CategoryPropertyKey key = new CategoryPropertyKey();
        Advert advert = new Advert();
        CategoryPropertyValue value = CategoryPropertyValue.builder().build();
        when(repository.save(org.mockito.ArgumentMatchers.any(CategoryPropertyValue.class))).thenReturn(value);

        service.saveValue(key, "123", advert);

        verify(repository).save(org.mockito.ArgumentMatchers.any(CategoryPropertyValue.class));
    }
}
