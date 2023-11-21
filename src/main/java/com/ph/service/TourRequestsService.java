package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.TourRequest;
import com.ph.domain.entities.User;
import com.ph.domain.enums.Status;
import com.ph.exception.customs.ConflictException;
import com.ph.exception.customs.RelatedFieldException;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.TourRequestsMapper;
import com.ph.payload.request.TourRequestRequest;
import com.ph.payload.response.TourRequestsFullResponse;
import com.ph.payload.response.TourRequestsStatusResponse;
import com.ph.payload.response.TourRequestsResponse;
import com.ph.repository.TourRequestsRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TourRequestsService {
    private final TourRequestsRepository tourRequestsRepository;
    private final TourRequestsMapper tourRequestsMapper;
    private final UserService userService;
    private final AdvertService advertService;
    private final MessageUtil messageUtil;
    private final LogService logService;


    // Not :S05 - Save() *************************************************************************
    public ResponseEntity<?> save(TourRequestRequest request, UserDetails userDetails) {
        TourRequest tourRequest = request.get();
        //requestten gelen tourDate ve tourTime var mı yok mu  ve tam saatlerde mi kontrolu
        if (tourRequestsRepository.existsByTourDateAndTourTime(tourRequest.getTourDate(), tourRequest.getTourTime())) {
            throw  new ConflictException(messageUtil.getMessage("error.tourtime.conflict"));
        }
        if (!isValidTourTime(tourRequest.getTourTime()) ){
            throw  new RelatedFieldException(messageUtil.getMessage("error.tourtime.bad-request"));
        }
        Advert advert=advertService.getById(request.getAdvertId());
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.user.not-found.id")));
        User ownerUser=advert.getUser();
        tourRequest.setAdvert(advert);
        tourRequest.setGuestUser(user);
        tourRequest.setOwnerUser(ownerUser);
        TourRequest saved = tourRequestsRepository.save(tourRequest);
        logService.logMessage(messageUtil.getMessage("tour-request.created"), advert, user);
        return ResponseEntity.ok(tourRequestsMapper.toTourRequestsSaveResponse(tourRequest));
    }

    // Not :Time'i sadece tam saatlerde almamızı saglayan yardımcı method
    private boolean isValidTourTime(LocalTime tourTime) {
        int minute = tourTime.getMinute();
        return (minute == 00 || minute==30);  // Eğer dakika 0 ise (tam saat), true döndür
    }


    // Not :S06 - update() ****************************************************************************
    public ResponseEntity<?> update(Long tourId, TourRequestRequest request) {

        //gelen id ye ait tourRequest var mı yokmu kontrolu
        TourRequest tourRequest = tourRequestsRepository.findById(tourId).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.tour-request.not-found")));

        if ((Status.APPROVED.name()).equals(tourRequest.getStatus().name()) || (Status.CANCELED.name()).equals(tourRequest.getStatus().name())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageUtil.getMessage("error.tour-request.pending-or-declined"));
        }
        if (tourRequestsRepository.existsByTourDateAndTourTime(tourRequest.getTourDate(), tourRequest.getTourTime())) {
            throw  new ConflictException(messageUtil.getMessage("error.tourtime.conflict"));
        }
        if (!isValidTourTime(tourRequest.getTourTime()) ){
            throw  new RelatedFieldException(messageUtil.getMessage("error.tourtime.bad-request"));
        }

        tourRequest.setTourDate(request.getTourDate());
        tourRequest.setTourTime(request.getTourTime());
        tourRequest.setStatus(Status.PENDING);
        tourRequestsRepository.save(tourRequest);
        return ResponseEntity.ok(tourRequestsMapper.toTourRequestsSaveResponse(tourRequest));
    }

    // Not :S01 - GetAllTourRequestByCustomerAsPage() ***************************************************
    public Page<TourRequestsStatusResponse> getAllTourRequestByCustomerAsPage(UserDetails userDetails, int page, int size, String sort, String type) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.user.not-found.id")));

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return tourRequestsRepository.findAllByGuestUser_Id(user.getId(), pageable)
                .map(tourRequestsMapper::toTourRequestsResponse);
    }

    // Not:S10 - deleteTourRequest() *******************************************************************
    public ResponseEntity<TourRequestsResponse> deleteTourRequest(Long id) {
        TourRequest tourRequest = tourRequestsRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.tour-request.not-found")));

        tourRequestsRepository.deleteById(id);
        return ResponseEntity.ok(tourRequestsMapper.toTourRequestsSaveResponse(tourRequest));

    }

    // Not :S02 - GetAllTourRequestByManagerAndAdminAsPage() ***************************************************
    public Page<TourRequestsFullResponse> getAllTourRequestByManagerAndAdminAsPage(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return tourRequestsRepository.findAll(pageable)
                .map(tourRequestsMapper::toTourRequestsFullResponse);
    }

    // Not :S03 - GetTourRequestByCustomerAsTourId() *******************************************************************

    public ResponseEntity<TourRequestsFullResponse> getTourRequestByCustomerId(UserDetails userDetails, Long tourId) {

        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.user.not-found.id")));

        TourRequest tourRequest = tourRequestsRepository.findByIdAndGuestUser_Id(tourId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.tour-request.not-found")));
        return ResponseEntity.ok(tourRequestsMapper.toTourRequestsFullResponse(tourRequest));
    }

    // Not :S04 - GetTourRequestByManagerAndAdminAsTourId() **************************************************

    public ResponseEntity<TourRequestsFullResponse> getTourRequestByManagerAndAdminId(Long tourId) {

        TourRequest tourRequest = tourRequestsRepository.findById(tourId).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.tour-request.not-found")));

        return ResponseEntity.ok(tourRequestsMapper.toTourRequestsFullResponse(tourRequest));
    }

    // Not :S07 - CancelByCustomerAsTourId() *******************************************************************


    public ResponseEntity<TourRequestsResponse> cancelByCustomerAsTourId(Long tourId, UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.user.not-found")));
        TourRequest tourRequest = tourRequestsRepository.findByIdAndGuestUser_Id(tourId,user.getId()).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.tour-request.not-found")));
        tourRequest.setStatus(Status.CANCELED);
        tourRequestsRepository.save(tourRequest);
        logService.logMessage(messageUtil.getMessage("tour-request.canceled"), tourRequest.getAdvert(), tourRequest.getGuestUser());
        return ResponseEntity.ok(tourRequestsMapper.toTourRequestsSaveResponse(tourRequest));
    }

    // Not :S08 - ApproveByCustomerAsTourId() *******************************************************************

    public ResponseEntity<TourRequestsStatusResponse> approveByCustomerAsTourId(Long tourId) {
        TourRequest tourRequest = tourRequestsRepository.findById(tourId).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.tour-request.not-found")));
        tourRequest.setStatus(Status.APPROVED);
        tourRequestsRepository.save(tourRequest);
        logService.logMessage(messageUtil.getMessage("tour-request.approved"), tourRequest.getAdvert(), tourRequest.getGuestUser());
        return ResponseEntity.ok(tourRequestsMapper.toTourRequestsResponse(tourRequest));
    }

    // Not :S09 - DeclinedByCustomerAsTourId() *******************************************************************

    public ResponseEntity<TourRequestsStatusResponse> declinedByCustomerAsTourId(Long tourId) {
        TourRequest tourRequest = tourRequestsRepository.findById(tourId).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.tour-request.not-found")));
        tourRequest.setStatus(Status.DECLINED);
        tourRequestsRepository.save(tourRequest);
        logService.logMessage(messageUtil.getMessage("tour-request.declined"), tourRequest.getAdvert(), tourRequest.getGuestUser());
        return ResponseEntity.ok(tourRequestsMapper.toTourRequestsResponse(tourRequest));
    }

    // Not : GetById() ***************************************************************************************

    public TourRequest getById(Long id){
      return tourRequestsRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.tour-request.not-found")));
    }




}