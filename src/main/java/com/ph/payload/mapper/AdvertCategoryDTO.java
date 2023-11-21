package com.ph.payload.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
public class AdvertCategoryDTO {
    private String categoryName;
    private Long categoryQuantity;




}
