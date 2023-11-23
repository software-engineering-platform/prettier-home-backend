package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Image;
import com.ph.exception.customs.ImageException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.ImageMapper;
import com.ph.payload.request.ImageRequest;
import com.ph.payload.response.ImageResponse;
import com.ph.repository.AdvertRepository;
import com.ph.repository.ImageRepository;
import com.ph.utils.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final AdvertRepository advertRepository;


    public ImageResponse createImage(MultipartFile request, Long id, boolean featured )   {
        Image image = null;
        try {
            image = Image.builder()
                    .name(request.getOriginalFilename())
                    .featured(featured)
                    .type(request.getContentType())
                    .data(ImageUtil.compressImage(request.getBytes()))
                    .advert(advertRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException(String.format("Advert not found with id: %s", id))))
                    .build();
        } catch (IOException e) {
            throw new ImageException(e.getMessage());
        }
        Image savedImage = imageRepository.save(image);
        return imageMapper.toImageResponse(savedImage);


    }

    public ResponseEntity<?> getImageById(Long id) {
        Image found = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Image not found with id: %s", id)));

       ImageResponse response = ImageResponse.builder()
                .id(found.getId())
                .name(found.getName())
                .type(found.getType())
                .featured(found.isFeatured())
                .data(ImageUtil.decompressImage(found.getData()))
                .advertId(found.getAdvert().getId())
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(found.getType()))
                .body(ImageUtil.decompressImage(found.getData()));


    }

    public List<ImageResponse> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(imageMapper::toImageResponse).toList();
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }


}
