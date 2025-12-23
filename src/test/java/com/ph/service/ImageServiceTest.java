package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Image;
import com.ph.exception.customs.ImageException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.ImageMapper;
import com.ph.payload.mapper.ImageMapper;
import com.ph.payload.request.ImageUpdateRequest;
import com.ph.payload.response.ImageResponse;
import com.ph.repository.AdvertRepository;
import com.ph.repository.ImageRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ImageMapper imageMapper;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private ImageService imageService;

    @Test
    void createImage_throwsWhenEmpty() {
        when(messageUtil.getMessage("error.image.not.found")).thenReturn("missing");

        assertThatThrownBy(() -> imageService.createImage(List.of(), 1L))
                .isInstanceOf(ImageException.class);
    }

    @Test
    void deleteImage_throwsWhenDeletingAllImages() {
        Advert advert = new Advert();
        advert.setId(10L);
        when(advertRepository.findByImages_Id(1L)).thenReturn(advert);
        when(imageRepository.countByAdvert_Id(10L)).thenReturn(2L);
        when(messageUtil.getMessage("error.image.not.delete")).thenReturn("cannot delete");

        assertThatThrownBy(() -> imageService.deleteImage(List.of(1L, 2L)))
                .isInstanceOf(ImageException.class);
    }

    @Test
    void updateImage_setsFeaturedFlag() {
        Image featured = new Image();
        featured.setId(1L);
        featured.setFeatured(true);
        Image target = new Image();
        target.setId(2L);
        target.setFeatured(false);
        Advert advert = new Advert();
        advert.setId(5L);
        advert.setImages(List.of(featured, target));

        when(advertRepository.findById(5L)).thenReturn(Optional.of(advert));
        when(imageRepository.findById(2L)).thenReturn(Optional.of(target));
        when(imageMapper.toImageResponse(any(Image.class))).thenReturn(new ImageResponse());

        ImageUpdateRequest request = ImageUpdateRequest.builder().advertId(5L).imgId(2L).build();

        imageService.updateImage(request);

        verify(imageRepository).save(featured);
        verify(imageRepository).save(target);
    }

    @Test
    void updateImage_throwsWhenImageNotInAdvert() {
        Image target = new Image();
        target.setId(2L);
        Advert advert = new Advert();
        advert.setId(5L);
        advert.setImages(List.of());

        when(advertRepository.findById(5L)).thenReturn(Optional.of(advert));
        when(messageUtil.getMessage("error.image.not.found.id")).thenReturn("missing");

        ImageUpdateRequest request = ImageUpdateRequest.builder().advertId(5L).imgId(2L).build();

        assertThatThrownBy(() -> imageService.updateImage(request))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
