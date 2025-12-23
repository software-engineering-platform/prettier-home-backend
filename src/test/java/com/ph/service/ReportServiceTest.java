package com.ph.service;

import com.ph.aboutexcel.ExcelWriteService;
import com.ph.payload.mapper.ReportMapper;
import com.ph.payload.response.StatisticsResponse;
import com.ph.aboutexcel.ExcelWriteService;
import com.ph.repository.AdvertRepository;
import com.ph.repository.AdvertTypeRepository;
import com.ph.repository.CategoryRepository;
import com.ph.repository.TourRequestsRepository;
import com.ph.repository.UserRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ExcelWriteService excelWriteService;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdvertTypeRepository advertTypeRepository;

    @Mock
    private TourRequestsRepository tourRequestsRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ReportMapper reportMapper;

    @Mock
    private MessageUtil messageUtil;

    @InjectMocks
    private ReportService reportService;

    @Test
    void getStatistics_returnsMappedCounts() {
        when(advertRepository.count()).thenReturn(1L);
        when(userRepository.count()).thenReturn(2L);
        when(advertTypeRepository.count()).thenReturn(3L);
        when(tourRequestsRepository.count()).thenReturn(4L);
        when(categoryRepository.count()).thenReturn(5L);

        StatisticsResponse response = StatisticsResponse.builder()
                .totalAdverts(1L)
                .totalUsers(2L)
                .totalAdvertTypes(3L)
                .totalTourRequests(4L)
                .totalCategories(5L)
                .build();

        when(reportMapper.toStatisticsResponse(1L, 2L, 3L, 4L, 5L)).thenReturn(response);

        var result = reportService.getStatistics();

        assertThat(result.getBody()).isEqualTo(response);
    }
}
