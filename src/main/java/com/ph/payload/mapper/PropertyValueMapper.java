package com.ph.payload.mapper;

import com.ph.domain.entities.CategoryPropertyValue;
import com.ph.payload.response.PropertyValueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PropertyValueMapper {

    public List<PropertyValueResponse> entityToResponse(List<CategoryPropertyValue> entities) {
        return entities.stream()
                .map(entity -> PropertyValueResponse.builder()
                        .id(entity.getId())
                        .value(entity.getValue())
                        .build())
                .collect(Collectors.toList());
    }


}

