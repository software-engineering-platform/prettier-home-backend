package com.ph.service;

import com.ph.domain.entities.DailyReport;
import com.ph.domain.entities.TourRequest;
import com.ph.domain.enums.Status;
import com.ph.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final EmailService emailService;
    private final TourRequestsService tourRequestsService;
    private final AdvertRepository advertRepository;
    private final TourRequestsRepository tourRequestsRepository;
    private final FavoritesRepository favoritesRepository;
    private final UserRepository userRepository;
    private final DailyReportRepository dailyReportRepository;

    @Scheduled(cron = "${scheduler.cron.daily-at-midnight}")
    public void sendReminderEmails() {
        List<TourRequest> tourRequestList = tourRequestsService.checkingTheDatesOfTourRequests();
        if (tourRequestList.size() > 0) {
            tourRequestList.forEach(emailService::sendInformationEmail);
        }

    }

    @Scheduled(cron = "${scheduler.cron.daily-at-midnight}")
    public void updateExpiredPendingTourRequests() {
        List<TourRequest> expiredPendingTourRequests = tourRequestsService.getExpiredPendingTourRequests();
        if (!expiredPendingTourRequests.isEmpty()) {
            tourRequestsService.declineExpiredPendingTourRequests(expiredPendingTourRequests);
        }
    }

    @Scheduled(cron = "${scheduler.cron.daily-at-midnight}")
    public void saveDailyReport(){
     DailyReport dailyReport =   DailyReport.builder()
                .date(LocalDate.now())
                .numberOfAdverts(advertRepository.countActivatedAdverts())
                .numberOfTourRequests(tourRequestsRepository.count())
                .numberOfFavorites(favoritesRepository.count())
                .numberOfUsers(userRepository.count())
                .build();
     dailyReportRepository.save(dailyReport);
    }


    public List<DailyReport> getDailyReport(){

        // Burda son 7 günün raporunu alıyorum
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(7);
        List<DailyReport> reports = dailyReportRepository.findByDateBetween(startDate, currentDate);

        // Raporları tarihe göre sırala (en yeni tarihten en eskiye doğru)
        reports.sort(Comparator.comparing(DailyReport::getDate).reversed());
        return reports;
    }

}