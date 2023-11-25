package com.ph.service;

 import com.ph.domain.entities.Advert;
 import com.ph.domain.entities.Image;
import com.ph.exception.customs.ImageException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.ImageMapper;

 import com.ph.payload.response.ImageResponse;
import com.ph.repository.AdvertRepository;
import com.ph.repository.ImageRepository;
import com.ph.utils.ImageUtil;
 import com.ph.utils.MessageUtil;
 import jakarta.transaction.Transactional;
 import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
 import java.util.Comparator;
 import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final AdvertRepository advertRepository;
    private final MessageUtil messageUtil;


    public Image saveImage(MultipartFile request ,Long advertId){
        Image image = null;
        try {
            image = Image.builder()
                    .name(request.getOriginalFilename())
                    .featured(false)
                    .type(request.getContentType())
                    .data(ImageUtil.compressImage(request.getBytes()))
                    .advert(advertRepository.findById(advertId)
                            .orElseThrow(() -> new ResourceNotFoundException(String.format(messageUtil.getMessage("error.advert.not.found"), advertId))))
                    .build();
        } catch (IOException e) {
            throw new ImageException(e.getMessage());
        }
        return imageRepository.save(image);
    }




    public ImageResponse createImage(List<MultipartFile> request, Long advertId  )   {



    if (request.isEmpty()) {
    throw new ImageException(String.format(messageUtil.getMessage("error.image.not.found")));
    }



  List<Image> savedImage =  request.stream().map(t->saveImage(t, advertId)).toList();


        return imageMapper.toImageResponse(savedImage.get(0));


    }

    public ResponseEntity<?> getImageById(Long id) {
        Image found = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(messageUtil.getMessage("error.image.not.found.id"), id)));



        return ResponseEntity.ok(imageMapper.toImageResponse(found));


    }

    public List<ImageResponse> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream().map(imageMapper::toImageResponse).toList();
    }

    public void deleteImage(List<Long> id) {
      Advert advert=  advertRepository.findByImages_Id(id.get(0));

      if (advert.getImages().size()==id.size()){
          throw new ImageException(String.format(messageUtil.getMessage("error.image.not.delete")));
      }

        imageRepository.deleteAllById(id);
    }


    @Transactional
    public ImageResponse updateImage(Long imgId, Long advertId) {
      Advert advert =  advertRepository.findById(advertId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(messageUtil.getMessage("error.advert.not.found"), advertId)));

      List<Image> advertImages = advert.getImages();

        boolean match = advertImages.stream().anyMatch(image -> image.getId().equals(imgId));
        if (!match) {
            throw new ResourceNotFoundException(String.format(messageUtil.getMessage("error.image.not.found.id"), imgId));
        }

        Image found = imageRepository.findById(imgId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(messageUtil.getMessage("error.image.not.found.id"), imgId)));


         Image featuredImage =   advertImages.stream().filter(Image::isFeatured).findFirst().orElse(null);
        if (featuredImage != null) {
            featuredImage.setFeatured(false);
            imageRepository.save(featuredImage);
        }


        found.setFeatured(true);
        imageRepository.save(found);

        return imageMapper.toImageResponse(found);







    }

    @Transactional
    public List<ImageResponse> getAllImagesByAdvertId(Long id) {
        Advert advert =  advertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(messageUtil.getMessage("error.advert.not.found"), id)));
        List<Image> advertImages = advert.getImages();

        advertImages.sort(Comparator.comparing(Image::isFeatured).reversed());

        return advertImages.stream().map(imageMapper::toImageResponse).toList();
    }
}
