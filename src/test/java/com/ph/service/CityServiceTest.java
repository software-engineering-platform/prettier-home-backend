package com.ph.service;

import com.ph.domain.entities.City;
import com.ph.payload.mapper.LocationMapper;
import com.ph.payload.response.CityResponse;
import com.ph.repository.CityRepository;
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
class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private CityService cityService;

    @Test
    void getAllCities_mapsResults() {
        City city = new City();
        CityResponse response = new CityResponse();
        when(cityRepository.findAll()).thenReturn(List.of(city));
        when(locationMapper.toCityResponse(city)).thenReturn(response);

        List<CityResponse> result = cityService.getAllCities();

        assertThat(result).containsExactly(response);
    }

    @Test
    void getById_throwsWhenMissing() {
        when(cityRepository.findById(2L)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("error.city.not-found")).thenReturn("not found");

        assertThatThrownBy(() -> cityService.getById(2L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
