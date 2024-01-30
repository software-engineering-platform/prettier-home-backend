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
import java.util.stream.Collectors;


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
                .numberOfTourRequests((int) tourRequestsRepository.count())
                .numberOfFavorites((int) favoritesRepository.count())
                .numberOfUsers((int) userRepository.count())
                .build();
     dailyReportRepository.save(dailyReport);
    }


    public Map<String, Map<LocalDate, Integer>> getDailyReport() {
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = currentDate.minusDays(7);
        List<DailyReport> reports = dailyReportRepository.findByDateBetween(startDate, currentDate).stream().sorted(Comparator.comparing(DailyReport::getDate)).toList();
        Map<String, Map<LocalDate, Integer>> resultMap = new TreeMap<>(); // TreeMap kullan

        for (DailyReport report : reports) {
            LocalDate date = report.getDate();

            // numberOfAdverts

//            resultMap.put("Adverts",Map.of(date,report.getNumberOfAdverts()));
//            resultMap.put("Favorites",Map.of(date,report.getNumberOfFavorites()));
//            resultMap.put("TourRequests",Map.of(date,report.getNumberOfTourRequests()));
//            resultMap.put("Users",Map.of(date,report.getNumberOfUsers()));

            resultMap.computeIfAbsent("Adverts", k -> new TreeMap<>())
                    .merge(date, report.getNumberOfAdverts(), Integer::sum);

            System.err.println(resultMap);

            // numberOfFavorites
            resultMap.computeIfAbsent("Favorites", k -> new TreeMap<>())
                    .merge(date, report.getNumberOfFavorites(), Integer::sum);

            // numberOfTourRequests
            resultMap.computeIfAbsent("TourRequests", k -> new TreeMap<>())
                    .merge(date, report.getNumberOfTourRequests(), Integer::sum);

            // numberOfUsers
            resultMap.computeIfAbsent("Users", k -> new TreeMap<>())
                    .merge(date, report.getNumberOfUsers(), Integer::sum);
        }
        return resultMap;
    }





}