package com.ph.payload.mapper;

import com.ph.domain.entities.TourRequest;
import com.ph.payload.response.TourRequestsFullResponse;
import com.ph.payload.response.TourRequestsStatusResponse;
import com.ph.payload.response.TourRequestsResponse;
import org.springframework.stereotype.Component;

@Component
public class TourRequestsMapper {
    public TourRequestsStatusResponse toTourRequestsResponse(TourRequest tourRequest) {
        return TourRequestsStatusResponse.builder()
                .id(tourRequest.getId())
                .tourDate(tourRequest.getTourDate())
                .advert(tourRequest.getAdvert())
                .ownerUser(tourRequest.getOwnerUser())
                .status(tourRequest.getStatus())
                .build();
    }

    public TourRequestsFullResponse toTourRequestsFullResponse(TourRequest tourRequest) {
        return TourRequestsFullResponse.builder()
                .id(tourRequest.getId())
                .tourDate(tourRequest.getTourDate())
                .advert(tourRequest.getAdvert())
                .ownerUser(tourRequest.getOwnerUser())
                .guestUser(tourRequest.getGuestUser())
                .status(tourRequest.getStatus())
                .build();
    }

    public TourRequestsResponse toTourRequestsSaveResponse(TourRequest tourRequest) {
        return TourRequestsResponse.builder()
                .id(tourRequest.getId())
                .tourDate(tourRequest.getTourDate())
                .tourTime(tourRequest.getTourTime())
                .status(tourRequest.getStatus())
                .build();
    }

}
