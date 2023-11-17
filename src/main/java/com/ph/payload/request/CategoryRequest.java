package com.ph.payload.request;

import com.ph.domain.entities.Category;
import com.ph.domain.entities.CategoryPropertyKey;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true) //toBuilder()  yontemi ile mevcut nesnenin bir kopyasini olusturmak icin kullanilir. nesne uzerinden belirli kisimlar degisitirilerek yeni bir nesne olusturulabilir.
public class CategoryRequest implements Supplier<Category>, Serializable {

    @NotNull(message = "Title cannot be null")
    @Size(max = 150, message = "Title must be less than 150 characters")
    private String title;

    @NotNull(message = "Icon cannot be null")
    @Size(max = 50, message = "Icon must be less than 50 characters")
    private String icon;

    @NotNull(message = "Seq cannot be null")
    private int seq;

    @NotNull(message = "Is Active cannot be null")
    private boolean active;


    @Override
    public Category get() {

        return Category.builder()
                .title(getTitle())
                .icon(getIcon())
                .seq(getSeq())
                .active(isActive())
                .build();
    }


}
