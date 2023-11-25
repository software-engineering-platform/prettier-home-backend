package com.ph.service;
import com.ph.domain.entities.District;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.LocationMapper;
import com.ph.payload.response.DistrictResponse;
import com.ph.repository.DistrictsRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistrictsService {
    private final DistrictsRepository districtsRepository;
    private final MessageUtil messageUtil;
    private final LocationMapper locationMapper;


    //Not:U03 GetAllDistricts() *************************************************************************
    public List<DistrictResponse> getAllDistricts() {

        return  districtsRepository.findAll().stream().map(locationMapper::toDistrictResponse).collect(Collectors.toList());

    }

    // Not : GetById() ***************************************************************************************
    public District getById(Long id){
        return districtsRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.district.not-found")));
    }
}
