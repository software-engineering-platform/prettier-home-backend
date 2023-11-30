package com.ph.payload.request;

import com.ph.domain.entities.CategoryPropertyKey;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class    CategoryPropertyKeyRequest implements Supplier<CategoryPropertyKey>, Serializable {

    @NotNull(message = "{validation.category.name.notnull}")
    @Size(min = 2, max = 80, message = "{validation.category.name.size}")
    private String name;


    @Override
    public CategoryPropertyKey get() {

        return CategoryPropertyKey.builder()
                .name(getName())
                .build();
    }


}
