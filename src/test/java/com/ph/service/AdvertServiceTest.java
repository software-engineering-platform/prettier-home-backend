package com.ph.service;

import com.ph.domain.entities.*;
import com.ph.exception.customs.BuiltInFieldException;
import com.ph.payload.mapper.AdvertMapper;
import com.ph.payload.request.AdvertRequest;
import com.ph.payload.request.abstracts.AdvertRequestAbs;
import com.ph.repository.AdvertRepository;
import com.ph.repository.CategoryRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertServiceTest {

    @Mock
    private AdvertRepository repository;

    @Mock
    private AdvertTypeService typeService;

    @Mock
    private AdvertMapper mapper;

    @Mock
    private ImageService imageService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryPropertyValueService propertyValueService;

    @Mock
    private CityService cityService;

    @Mock
    private DistrictsService districtService;

    @Mock
    private CountriesService countriesService;

    @Mock
    private LogService logService;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private AdvertService service;

    @Test
    void delete_throwsWhenBuiltIn() {
        Advert advert = new Advert();
        advert.setBuiltIn(true);
        User user = new User();
        when(repository.findById(1L)).thenReturn(Optional.of(advert));
        when(messageUtil.getMessage("error.advert.builtin")).thenReturn("built-in");

        assertThatThrownBy(() -> service.delete(1L, user))
                .isInstanceOf(BuiltInFieldException.class);
    }

    @Test
    void create_savesAdvertAndImagesAndPropertyValues() {
        AdvertRequest request = org.mockito.Mockito.mock(AdvertRequest.class);
        Advert advert = new Advert();
        advert.setId(1L);
        advert.setTitle("Test Advert");
        User user = new User();
        user.setId(2L);

        Category category = new Category();
        category.setId(10L);
        CategoryPropertyKey key1 = new CategoryPropertyKey();
        CategoryPropertyKey key2 = new CategoryPropertyKey();
        category.setCategoryPropertyKeys(List.of(key1, key2));

        AdvertType advertType = new AdvertType();
        Country country = new Country();
        City city = new City();
        District district = new District();

        when(mapper.toEntity(request)).thenReturn(advert);
        when(request.getAdvertTypeId()).thenReturn(11L);
        when(request.getCountryId()).thenReturn(12L);
        when(request.getCityId()).thenReturn(13L);
        when(request.getDistrictId()).thenReturn(14L);
        when(request.getCategoryId()).thenReturn(10L);
        when(typeService.getById(11L)).thenReturn(advertType);
        when(countriesService.getById(12L)).thenReturn(country);
        when(cityService.getById(13L)).thenReturn(city);
        when(districtService.getById(14L)).thenReturn(district);
        when(categoryRepository.findById(10L)).thenReturn(Optional.of(category));
        when(request.getPropertyValues()).thenReturn(List.of("10", "20"));
        when(repository.save(any(Advert.class))).thenReturn(advert);

        List<MultipartFile> images = List.of(org.mockito.Mockito.mock(MultipartFile.class));

        service.create(request, images, user);

        verify(imageService).createImage(images, 1L);
        verify(propertyValueService, times(2)).saveValue(any(CategoryPropertyKey.class), any(String.class), eq(advert));
        verify(repository, atLeastOnce()).save(advert);
        ArgumentCaptor<String> logCaptor = ArgumentCaptor.forClass(String.class);
        verify(logService).logMessage(logCaptor.capture(), eq(advert), eq(user));
        assertThat(logCaptor.getValue()).contains("Advert created by");
    }
}
