package com.ph.payload.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryPropertyValueRequest {

    @NotNull(message = "Value cannot be null")
    @Size(max = 100, message = "Value must be less than 100 characters")
    private String value;
}
