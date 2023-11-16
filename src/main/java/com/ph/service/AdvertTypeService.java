package com.ph.service;


import com.ph.domain.entities.AdvertType;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.AdvertTypeMapper;
import com.ph.payload.request.AdvertTypeRequest;
import com.ph.payload.response.AdvertTypeResponse;
import com.ph.repository.AdvertTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertTypeService {

    private final AdvertTypeRepository repository;
    private final AdvertTypeMapper mapper;


    /*
  !!! Create new AdvertType method start.
    */
    public ResponseEntity<AdvertTypeResponse> create(AdvertTypeRequest request) {
        AdvertType advertType = mapper.toEntity(request);
        repository.save(advertType);
        return ResponseEntity.ok(mapper.toResponse(advertType));
    }
    /*
        !!! Create new AdvertType method end.
         */


    /*
    !!! HELPER METHOD START .
     */
    public AdvertType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AdvertType not found by id: " + id));
    }
      /*
    !!! HELPER METHOD END .
     */

    /*
    !!! Get AdvertType by id method start.
     */
    public ResponseEntity<AdvertTypeResponse> get(Long id) {
        return ResponseEntity.
                ok(mapper.toResponse(getById(id)));
    }

      /*
    !!! Get AdvertType by id method end.
     */

      /*
    !!! Get All  AdvertTypes method start.
     */

    public ResponseEntity<List<AdvertTypeResponse>> getAll() {

        return ResponseEntity.ok(repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList()));
    }

    /*
    !!! Get All  AdvertTypes method end.
     */


    /*
    !!! Delete AdvertType method start.
     */
    public ResponseEntity<AdvertTypeResponse> delete(Long id) {
        AdvertType advertType = getById(id);
        repository.delete(advertType);
        return ResponseEntity.ok(mapper.toResponse(advertType));
    }

     /*
    !!! Delete AdvertType method end.
     */


    /*
    !!! Update AdvertType method start.
     */

    public ResponseEntity<AdvertTypeResponse> update(Long id, AdvertTypeRequest request) {
        getById(id);
        AdvertType advertType =  mapper.toEntity(request);
        advertType.setId(id);
        AdvertType updatedAdvertType = repository.save(advertType);
        return ResponseEntity.ok(mapper.toResponse(updatedAdvertType));
    }

      /*
    !!! Update AdvertType method end.
     */


}
