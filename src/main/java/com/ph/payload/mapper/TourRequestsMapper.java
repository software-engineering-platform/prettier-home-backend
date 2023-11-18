package com.ph.payload.mapper;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.TourRequest;
import com.ph.payload.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TourRequestsMapper {

   private final UserMapper userMapper;
    public TourRequestsStatusResponse toTourRequestsResponse(TourRequest tourRequest) {
        return TourRequestsStatusResponse.builder()
                .id(tourRequest.getId())
                .tourDate(tourRequest.getTourDate())
                .advert(toAdvertResponseForTourRequest(tourRequest.getAdvert()))
                .ownerUser(userMapper.toUserResponse(tourRequest.getOwnerUser()))
                .status(tourRequest.getStatus())
                .build();
    }

    public TourRequestsFullResponse toTourRequestsFullResponse(TourRequest tourRequest) {
        return TourRequestsFullResponse.builder()
                .id(tourRequest.getId())
                .tourDate(tourRequest.getTourDate())
                .advert(toAdvertResponseForTourRequest(tourRequest.getAdvert()))
                .ownerUser(userMapper.toUserResponse(tourRequest.getOwnerUser()))
                .guestUser(userMapper.toUserResponse(tourRequest.getGuestUser()))
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

    public AdvertResponseForTourRequest toAdvertResponseForTourRequest(Advert advert){
        return AdvertResponseForTourRequest.builder()
                .id(advert.getId())
                .price(advert.getPrice())
                .title(advert.getTitle())
                .city(advert.getCity())
                .images(advert.getImages())
                .location(advert.getLocation())
                .district(advert.getDistrict())
                .country(advert.getCountry())
                .build();
    }


}
