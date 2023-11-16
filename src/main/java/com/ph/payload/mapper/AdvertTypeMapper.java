package com.ph.payload.mapper;

import com.ph.domain.entities.AdvertType;
import com.ph.payload.request.AdvertTypeRequest;
import com.ph.payload.response.AdvertTypeResponse;
import org.springframework.stereotype.Component;

@Component
public class AdvertTypeMapper {

    public AdvertTypeResponse toResponse(AdvertType advertType) {
        return AdvertTypeResponse.builder()
                .id(advertType.getId())
                .title(advertType.getTitle())
                .build();
    }

    public AdvertType toEntity(AdvertTypeRequest request) {
        return AdvertType.builder()
                .title(request.getTitle())
                .build();
    }
}
