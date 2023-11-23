package com.ph.payload.request;

import com.ph.domain.entities.Image;
import lombok.*;

import java.io.Serializable;
import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest implements Supplier<Image>, Serializable {

    private byte[] data;
    private String name;
    private String type;
    private boolean featured;

    @Override
    public Image get() {
        return Image.builder()
                .data(getData())
                .name(getName())
                .type(getType())
                .featured(isFeatured())
                .build();
    }

}