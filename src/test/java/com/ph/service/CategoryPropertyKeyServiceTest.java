package com.ph.service;

import com.ph.domain.entities.Category;
import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.exception.customs.ConflictException;
import com.ph.exception.customs.NonDeletableException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.CategoryMapper;
import com.ph.payload.request.CategoryPropertyKeyRequest;
import com.ph.repository.CategoryPropertyKeyRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryPropertyKeyServiceTest {

    @Mock
    private CategoryPropertyKeyRepository propertyKeyRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryService categoryService;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private CategoryPropertyKeyService service;

    @Test
    void save_throwsOnDuplicateName() {
        CategoryPropertyKeyRequest request = org.mockito.Mockito.mock(CategoryPropertyKeyRequest.class);
        CategoryPropertyKey key = new CategoryPropertyKey();
        key.setName("Size");
        when(request.get()).thenReturn(key);
        when(categoryService.getCategoryById(1L)).thenReturn(new Category());
        when(propertyKeyRepository.existsByCategory_IdAndNameIgnoreCase(1L, "Size")).thenReturn(true);
        when(messageUtil.getMessage("error.cpk.save.duplicate.name.in.category")).thenReturn("dup");

        assertThatThrownBy(() -> service.save(request, 1L))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    void deletePropertyKey_throwsWhenMissing() {
        when(propertyKeyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deletePropertyKey(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updatePropertyKey_throwsWhenBuiltIn() {
        CategoryPropertyKey key = new CategoryPropertyKey();
        key.setBuiltIn(true);
        when(propertyKeyRepository.findById(1L)).thenReturn(Optional.of(key));
        when(messageUtil.getMessage("error.cpk.update.built-in")).thenReturn("built-in");

        assertThatThrownBy(() -> service.updatePropertyKey(1L, org.mockito.Mockito.mock(CategoryPropertyKeyRequest.class)))
                .isInstanceOf(NonDeletableException.class);
    }
}
