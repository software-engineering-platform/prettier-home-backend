package com.ph.service;

import com.ph.domain.entities.DailyReport;
import com.ph.domain.entities.TourRequest;
import com.ph.repository.AdvertRepository;
import com.ph.repository.ContactRepository;
import com.ph.repository.DailyReportRepository;
import com.ph.repository.FavoritesRepository;
import com.ph.repository.TourRequestsRepository;
import com.ph.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private TourRequestsService tourRequestsService;

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private TourRequestsRepository tourRequestsRepository;

    @Mock
    private FavoritesRepository favoritesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DailyReportRepository dailyReportRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private SchedulerService schedulerService;

    @Test
    void saveDailyReport_skipsWhenAlreadyExists() {
        when(dailyReportRepository.existsByDate(LocalDate.now())).thenReturn(true);

        schedulerService.saveDailyReport();

        verify(dailyReportRepository, never()).save(any(DailyReport.class));
    }

    @Test
    void updateExpiredPendingTourRequests_declinesWhenPresent() {
        List<TourRequest> expired = List.of(new TourRequest());
        when(tourRequestsService.getExpiredPendingTourRequests()).thenReturn(expired);

        schedulerService.updateExpiredPendingTourRequests();

        verify(tourRequestsService).declineExpiredPendingTourRequests(expired);
    }
}
