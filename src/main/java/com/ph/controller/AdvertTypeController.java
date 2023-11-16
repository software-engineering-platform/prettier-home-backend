package com.ph.controller;

import com.ph.domain.entities.AdvertType;
import com.ph.payload.request.AdvertTypeRequest;
import com.ph.payload.response.AdvertTypeResponse;
import com.ph.service.AdvertTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("advert-types")
@RequiredArgsConstructor
public class AdvertTypeController {

    private final AdvertTypeService service;

    /*
   !!! Create new AdvertType method start.
     */
    @PostMapping
    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<AdvertTypeResponse> create(@RequestBody @Valid AdvertTypeRequest request) {
        return service.create(request);
    }
     /*
  !!!  Create new AdvertType method end.
     */

    /*
       !!! Get AdvertType by id method start.
        */
    @GetMapping("{id}")
    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<AdvertTypeResponse> get(@PathVariable Long id) {
        return service.get(id);
    }

     /*
    !!! Get AdvertType by id method end.
     */


    /*
  !!! Get All AdvertType method start.
   */
    @GetMapping()
    public ResponseEntity<List<AdvertTypeResponse>> getAll() {
        return service.getAll();
    }
 /*
  !!! Get All AdvertType method end.
   */


    /*
  !!! Delete AdvertType method start.
     */
    @DeleteMapping("{id}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<AdvertTypeResponse> delete(@PathVariable Long id) {
        return service.delete(id);
    }
     /*
  !!! Delete AdvertType method end.
     */


    /*
  !!! Update AdvertType method start.
     */
    @PutMapping("{id}")
    // @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<AdvertTypeResponse> update(@PathVariable Long id, @Valid @RequestBody AdvertTypeRequest request) {
        return service.update(id, request);
    }

    /*
  !!! Update AdvertType method end.
     */
}
