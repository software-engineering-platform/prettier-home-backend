package com.ph.payload.mapper;

import com.ph.domain.entities.Image;
import com.ph.payload.response.ImageResponse;
import com.ph.utils.ImageUtil;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ImageMapper {
    public ImageResponse toImageResponse(Image savedImage) {
        return ImageResponse.builder()
                .id(savedImage.getId())
                .name(savedImage.getName())
                .type(savedImage.getType())
                .featured(savedImage.isFeatured())
                 .data(encodeImage(ImageUtil.decompressImage(savedImage.getData())))
                .advertId(savedImage.getAdvert().getId())
                .build();
    }

    private String encodeImage(byte[] imageData) {
        return Base64.getEncoder().encodeToString(imageData);
    }




}