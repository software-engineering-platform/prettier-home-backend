package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Log;
import com.ph.domain.entities.User;
import com.ph.payload.mapper.LogMapper;
import com.ph.repository.LogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @Mock
    private LogMapper logMapper;

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LogService logService;

    @Test
    void logMessage_persistsLog() {
        Advert advert = new Advert();
        User user = new User();

        logService.logMessage("message", advert, user);

        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);
        verify(logRepository).save(captor.capture());
        assertThat(captor.getValue().getMessage()).isEqualTo("message");
        assertThat(captor.getValue().getAdvert()).isEqualTo(advert);
        assertThat(captor.getValue().getUser()).isEqualTo(user);
    }
}
