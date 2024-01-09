package com.ph.service;

import com.ph.domain.entities.TourRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


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
}
