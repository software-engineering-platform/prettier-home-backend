package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Log;
import com.ph.domain.entities.User;
import com.ph.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);
    private final LogRepository logRepository;

    /**
     * Logs a message along with the advert and user information.
     * Saves a log entity to the database.
     *
     * @param message the message to log
     * @param advert  the advert object
     * @param user    the user object
     */
    public void logMessage(String message, Advert advert, User user) {
        LOGGER.info(message, advert, user);

        // Creating a log entity and saving it to the database
        Log log = Log.builder()
                .message(message)
                .createdAt(LocalDateTime.now())
                .advert(advert)
                .user(user)
                .build();
        logRepository.save(log);
    }


}
