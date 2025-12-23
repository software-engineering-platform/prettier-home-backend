package com.ph.service;

import com.ph.domain.entities.District;
import com.ph.payload.mapper.LocationMapper;
import com.ph.payload.response.DistrictResponse;
import com.ph.repository.DistrictsRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import com.ph.exception.customs.ResourceNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DistrictsServiceTest {

    @Mock
    private DistrictsRepository districtsRepository;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private DistrictsService districtsService;

    @Test
    void getAllDistricts_mapsResults() {
        District district = new District();
        DistrictResponse response = new DistrictResponse();
        when(districtsRepository.findAll()).thenReturn(List.of(district));
        when(locationMapper.toDistrictResponse(district)).thenReturn(response);

        List<DistrictResponse> result = districtsService.getAllDistricts();

        assertThat(result).containsExactly(response);
    }

    @Test
    void getById_throwsWhenMissing() {
        when(districtsRepository.findById(3L)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("error.district.not-found")).thenReturn("not found");

        assertThatThrownBy(() -> districtsService.getById(3L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
