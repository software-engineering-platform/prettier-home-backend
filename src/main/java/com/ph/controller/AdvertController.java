package com.ph.controller;

 import com.ph.payload.request.AdvertRequest;
import com.ph.payload.request.AdvertRequestForUpdateByAdmin;
import com.ph.payload.request.AdvertRequestForUpdateByCustomer;
import com.ph.payload.response.AdvertResponse;
import com.ph.service.AdvertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adverts")
@RequiredArgsConstructor
public class AdvertController {
    private final AdvertService service;




    // NOT:A01 / getByAdminPage() ************************************************************

    @GetMapping("/adverts")

    public Page<AdvertResponse> getByAnonymusPage(
            @RequestParam(value = "q", defaultValue = "", required = false) String query,
            @RequestParam(value = "category_id") Long categoryId,
            @RequestParam(value = "advert_type_id") Long advertTypeId,
            @RequestParam(value = "price_start", required = false) Integer priceStart,
            @RequestParam(value = "price_end", required = false) Integer priceEnd,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (type.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return service.getByAdminOrAnonymousPage(
                query,
                categoryId,
                advertTypeId,
                priceStart,
                priceEnd,
                status,
                pageable
        );

    }

    // NOT:A02 / getAdvertsByCities() ************************************************************

    @GetMapping("/adverts/cities")
    public List<Object[]> getAdvertsByCities(){
        return service.getAdvertsByCities();


    }


    // NOT:A03 / getAdvertsByCategories() ************************************************************

    @GetMapping("/adverts/categories")
    public List<Object[]> getAdvertsByCategories() {
        return service.getAdvertsByCategories();

    }


    // NOT:A04 / getMostPopularAdverts() ************************************************************
    @GetMapping("/adverts/popular/{amount}")
    public Page<AdvertResponse> getMostPopularAdverts(@PathVariable Integer amount){
        return service.getMostPopularAdverts(amount);
    }



    // NOT:A05 / getForCustomerById() ************************************************************

    @GetMapping("/adverts/auth")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public Page<AdvertResponse> getByCustomerPage(

            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "20", required = false) int size,
            @RequestParam(value = "sort", defaultValue = "category_id", required = false) String sort,
            @RequestParam(value = "type", defaultValue = "asc", required = false) String type,
            @AuthenticationPrincipal UserDetails userDetails, @RequestHeader("Authorization") String authorizationHeader

    ) {
        return service.getByCustomerPage(page, size, sort, type, userDetails, authorizationHeader);
    }


    // NOT:A06 / getByAdminPage() ************************************************************

    @GetMapping("/adverts/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public Page<AdvertResponse> getByAdminPage(
            @RequestParam(value = "q", defaultValue = "", required = false) String query,
            @RequestParam(value = "category_id") Long categoryId,
            @RequestParam(value = "advert_type_id") Long advertTypeId,
            @RequestParam(value = "price_start", required = false) Integer priceStart,
            @RequestParam(value = "price_end", required = false) Integer priceEnd,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "category_id") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (type.equals("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return service.getByAdminOrAnonymousPage(
                query,
                categoryId,
                advertTypeId,
                priceStart,
                priceEnd,
                status,
                pageable
        );
    }



    // NOT:A07 / getBySlug() ************************************************************

    @GetMapping("/{slug}")
    public ResponseEntity<AdvertResponse> getBySlug(@PathVariable String slug) {
        return service.getBySlug(slug);
    }


    // NOT:A08 / getForCustomer() ************************************************************

    @GetMapping("/{id}/auth")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<AdvertResponse> getByCustomer(@PathVariable Long id,
                                                        @AuthenticationPrincipal UserDetails userDetails, @RequestHeader("Authorization") String authorizationHeader) {
        return service.getByCustomer(id, userDetails, authorizationHeader);
    }



    // NOT:A09 / getForAdmin() ************************************************************

    @GetMapping("/{id}/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<AdvertResponse> getByAdmin(@PathVariable Long id) {
        return service.getByAdmin(id);
    }



    // NOT:A10 / save() ************************************************************

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<AdvertResponse> create(@RequestBody @Valid AdvertRequest request,
                                                 @AuthenticationPrincipal UserDetails userDetails, @RequestHeader("Authorization") String authorizationHeader) {
        return service.create(request, userDetails, authorizationHeader);
    }


    // NOT:A11 / updateForCustomer() ************************************************************

    @PutMapping("/auth/{id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<AdvertResponse> updateForCustomer(@PathVariable Long id,
                                                            @AuthenticationPrincipal UserDetails userDetails, @RequestHeader("Authorization") String authorizationHeader,
                                                            @RequestBody @Valid AdvertRequestForUpdateByCustomer request) {
        return service.updateForCustomer(id, request, userDetails, authorizationHeader);
    }

    // NOT:A12 / updateForAdmin() ************************************************************

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<AdvertResponse> update(@PathVariable Long id, @RequestBody @Valid AdvertRequestForUpdateByAdmin request) {
        return service.update(id, request);
    }



    // NOT:A13 / delete() ************************************************************

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    public ResponseEntity<AdvertResponse> delete(@PathVariable Long id) {
        return service.delete(id);
    }







    // NOT:A06 / getByAdminPage() ************************************************************ (ESKİ VE EKSİK OLAN)
/*
//            @GetMapping("/adverts/admin")
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
//    public Page<AdvertResponse> getByAdminPage(
//            @RequestParam(value = "query", defaultValue = "" ) String query,
//            @PageableDefault(sort = "category_id", direction = Sort.Direction.DESC, page = 0, size = 20) Pageable pageable) {
//        return service.getByAdminPage(pageable,query);
//            }
 */



}
