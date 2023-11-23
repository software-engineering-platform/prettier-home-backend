package com.ph.payload.mapper;

import com.ph.domain.entities.Image;
import com.ph.payload.response.ImageResponse;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageResponse toImageResponse(Image savedImage) {
        return ImageResponse.builder()
                .id(savedImage.getId())
                .name(savedImage.getName())
                .type(savedImage.getType())
                .featured(savedImage.isFeatured())
//                .data(savedImage.getData())
                .advertId(savedImage.getAdvert().getId())
                .build();
    }
}