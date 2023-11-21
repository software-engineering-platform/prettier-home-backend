package com.ph.payload.request;

 import com.ph.payload.request.abstracts.AdvertRequestAbs;
 import jakarta.annotation.Nullable;
 import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
 import lombok.experimental.SuperBuilder;
 import org.springframework.boot.context.properties.bind.DefaultValue;

 import java.util.List;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AdvertRequest extends AdvertRequestAbs {


   private List<String> propertyvalues;

    private List<Integer> imageUrl;




}
