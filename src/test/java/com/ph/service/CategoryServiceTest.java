package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Category;
import com.ph.exception.customs.ConflictException;
import com.ph.exception.customs.NonDeletableException;
import com.ph.exception.customs.RelatedFieldException;
import com.ph.payload.mapper.CategoryMapper;
import com.ph.payload.request.CategoryRequest;
import com.ph.repository.CategoryRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private AdvertService advertService;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void save_throwsOnDuplicateTitle() {
        CategoryRequest request = org.mockito.Mockito.mock(CategoryRequest.class);
        Category category = new Category();
        category.setTitle("House");
        when(request.get()).thenReturn(category);
        when(request.getTitle()).thenReturn("House");
        when(categoryRepository.existsByTitleIgnoreCase("House")).thenReturn(true);
        when(messageUtil.getMessage("error.category.save.exist")).thenReturn("exists");

        assertThatThrownBy(() -> categoryService.save(request))
                .isInstanceOf(ConflictException.class);
    }

    @Test
    void deleteCategory_throwsWhenBuiltIn() {
        Category category = new Category();
        category.setBuiltIn(true);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(messageUtil.getMessage("error.category.delete.built-in")).thenReturn("built-in");

        assertThatThrownBy(() -> categoryService.deleteCategory(1L))
                .isInstanceOf(NonDeletableException.class);
    }

    @Test
    void deleteCategory_throwsWhenRelatedAdvertsExist() {
        Category category = new Category();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(advertService.getAdvertsOfCategory(1L)).thenReturn(List.of(new Advert()));
        when(messageUtil.getMessage("error.category.delete.advert")).thenReturn("related");

        assertThatThrownBy(() -> categoryService.deleteCategory(1L))
                .isInstanceOf(RelatedFieldException.class);
    }
}
