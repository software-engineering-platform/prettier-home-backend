package com.ph.service;

import com.ph.domain.entities.City;
import com.ph.domain.entities.District;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.repository.CityRepository;
import com.ph.repository.DistrictsRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictsService {
    private final DistrictsRepository districtsRepository;
    private final MessageUtil messageUtil;

    //Not:U03 GetAllDistricts() *************************************************************************
    public List<District> getAllDistricts() {

        return  districtsRepository.findAll();
    }

    // Not : GetById() ***************************************************************************************
    public District getById(Long id){
        return districtsRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.district.not-found")));
    }
}
