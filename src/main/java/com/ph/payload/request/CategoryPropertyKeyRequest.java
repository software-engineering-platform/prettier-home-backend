package com.ph.payload.request;

import com.ph.domain.entities.Category;
import com.ph.domain.entities.CategoryPropertyKey;
import com.ph.payload.response.CategoryPropertyKeyResponse;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryPropertyKeyRequest implements Supplier<CategoryPropertyKey>, Serializable {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 80, message = "Name must be between 2 and 80 characters")
    private String name;

    private boolean builtIn;



    @Override
    public CategoryPropertyKey get() {
        return CategoryPropertyKey.builder()
                .name(getName())
                .builtIn(isBuiltIn())
                .build();
    }

}
