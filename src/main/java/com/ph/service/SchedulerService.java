package com.ph.service;

import com.ph.domain.entities.TourRequest;
import com.ph.domain.enums.Status;
import com.ph.repository.TourRequestsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final EmailService emailService;
    private final TourRequestsService tourRequestsService;

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
}