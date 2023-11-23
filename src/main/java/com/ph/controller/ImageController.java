package com.ph.controller;

 import com.ph.payload.response.ImageResponse;
import com.ph.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

 import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file,
                                         @RequestParam("advert") Long id,
                                         @RequestParam("type") boolean featured
    )    {

        ImageResponse response = imageService.createImage(file, id, featured);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {

        return   imageService.getImageById(id);
    }

    @GetMapping
    public ResponseEntity<List<ImageResponse>> getAllImages() {
        List<ImageResponse> responses = imageService.getAllImages();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }

}
