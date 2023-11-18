package com.ph.controller;


import com.ph.payload.request.TourRequestRequest;
import com.ph.payload.response.TourRequestsFullResponse;
import com.ph.payload.response.TourRequestsStatusResponse;
import com.ph.payload.response.TourRequestsResponse;
import com.ph.service.TourRequestsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tour-requests")
public class TourRequestsController {

    private final TourRequestsService tourRequestsService;

    // Not :S05 - Save() *************************************************************************
    // http://localhost:8080/tour-requests
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    @PostMapping()
    public ResponseEntity<?> save(@RequestBody @Valid TourRequestRequest request,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        return tourRequestsService.save(request, userDetails);
    }

    // Not :S06 - update() ****************************************************************************
    // http://localhost:8080/tour-requests/2/auth

    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    @PutMapping("/{id}/auth")
    public ResponseEntity<?> update(@PathVariable(name="id") Long tourId, @RequestBody @Valid TourRequestRequest request) {
        return tourRequestsService.update(tourId, request);
    }

    // Not :S01 - GetAllTourRequestByCustomerAsPage() ***************************************************
    // http://localhost:8080/tour-requests/auth
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    @GetMapping("/auth")
    public Page<TourRequestsStatusResponse> getAllTourRequestByCustomerAsPage(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "sort", defaultValue = "advert_id", required = false) String sort,
            @RequestParam(value = "type", defaultValue = "asc", required = false) String type

    ) {
        return tourRequestsService.getAllTourRequestByCustomerAsPage(userDetails, page, size, sort, type);
    }

    // Not:S10 - deleteTourRequest() *******************************************************************
    // http://localhost:8080/tour-requests/2
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<TourRequestsResponse> deleteTourRequest(@PathVariable Long id) {
        return tourRequestsService.deleteTourRequest(id);
    }


    // Not :S02 - GetAllTourRequestByManagerAndAdminAsPage() ***************************************************
    // http://localhost:8080/tour-requests/admin
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/admin")
    public Page<TourRequestsFullResponse> getAllTourRequestByManagerAndAdminAsPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "sort", defaultValue = "advert_id", required = false) String sort,
            @RequestParam(value = "type", defaultValue = "asc", required = false) String type
    ) {
        return tourRequestsService.getAllTourRequestByManagerAndAdminAsPage(page, size, sort, type);
    }

    // Not :S03 - GetTourRequestByCustomerAsTourId() ********************************************************
    // http://localhost:8080/tour-requests/6/auth
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    @GetMapping("/{id}/auth")
    public ResponseEntity<TourRequestsFullResponse> getTourRequestByCustomerId(
            @AuthenticationPrincipal UserDetails userDetails, @PathVariable(name="id") Long tourId) {
        return tourRequestsService.getTourRequestByCustomerId(userDetails, tourId);
    }

    // Not :S04 - GetTourRequestByManagerAndAdminAsTourId() **************************************************
    // http://localhost:8080/tour-requests/7/admin
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    @GetMapping("/{id}/admin")
    public ResponseEntity<TourRequestsFullResponse> getTourRequestByManagerAndAdminId(@PathVariable Long tourId) {
        return tourRequestsService.getTourRequestByManagerAndAdminId(tourId);

    }

    // Not :S07 - CancelByCustomerAsTourId() *******************************************************************
    // http://localhost:8080/tour-requests/6/cancel

    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<TourRequestsResponse> cancelByCustomerAsTourId(@PathVariable Long tourId, @AuthenticationPrincipal UserDetails userDetails) {
        return tourRequestsService.cancelByCustomerAsTourId(tourId, userDetails);
    }

    // Not :S08 - ApproveByCustomerAsTourId() *******************************************************************
    // http://localhost:8080/tour-requests/6/approve
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<TourRequestsStatusResponse> approveByCustomerAsTourId(@PathVariable Long tourId) {
        return tourRequestsService.approveByCustomerAsTourId(tourId);
    }

    // Not :S09 - DeclineByCustomerAsTourId() *******************************************************************
    // http://localhost:8080/tour-requests/6/decline
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")
    @PatchMapping("/{id}/decline")
    public ResponseEntity<TourRequestsStatusResponse> declineByCustomerAsTourId(@PathVariable Long tourId) {
        return tourRequestsService.declinedByCustomerAsTourId(tourId);
    }


}
