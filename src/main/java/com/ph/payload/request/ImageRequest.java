package com.ph.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
 import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest implements   Serializable {
    @NotNull(message = "Advert Id cannot be null")
    @Positive(message = "Advert Id must be a positive number")
     @JsonProperty("advertId")
        private Long advertId;



//    @NotNull(message = "Category Id cannot be null")
//    @Size(min=1, message = "Category Id cannot be empty")
//    @Pattern(regexp = "^[0-9]*$", message = "Category Id must be a number")
//    @JsonProperty("advertId")
//        private String categoryId;



}