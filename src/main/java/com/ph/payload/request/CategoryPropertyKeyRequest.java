package com.ph.payload.request;

import com.ph.domain.entities.Category;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CategoryPropertyKeyRequest {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 80, message = "Name must be between 2 and 80 characters")
    private String name;

    private boolean builtIn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
