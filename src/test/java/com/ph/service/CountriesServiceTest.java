package com.ph.service;

import com.ph.domain.entities.Country;
import com.ph.payload.mapper.LocationMapper;
import com.ph.payload.response.CountryResponse;
import com.ph.repository.CountryRepository;
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
class CountriesServiceTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private LocationMapper locationMapper;

    @InjectMocks
    private CountriesService countriesService;

    @Test
    void getAllCountries_mapsResults() {
        Country country = new Country();
        CountryResponse response = new CountryResponse();
        when(countryRepository.findAll()).thenReturn(List.of(country));
        when(locationMapper.toCountryResponse(country)).thenReturn(response);

        List<CountryResponse> result = countriesService.getAllCountries();

        assertThat(result).containsExactly(response);
    }

    @Test
    void getById_throwsWhenMissing() {
        when(countryRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("error.country.not-found")).thenReturn("not found");

        assertThatThrownBy(() -> countriesService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
