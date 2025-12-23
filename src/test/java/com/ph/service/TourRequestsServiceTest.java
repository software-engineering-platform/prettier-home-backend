package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.User;
import com.ph.exception.customs.RelatedFieldException;
import com.ph.payload.mapper.TourRequestsMapper;
import com.ph.payload.request.TourRequestRequest;
import com.ph.repository.TourRequestsRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TourRequestsServiceTest {

    @Mock
    private TourRequestsRepository tourRequestsRepository;

    @Mock
    private TourRequestsMapper tourRequestsMapper;

    @Mock
    private UserService userService;

    @Mock
    private AdvertService advertService;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private LogService logService;

    @InjectMocks
    private TourRequestsService service;

    @Test
    void save_throwsForInvalidTourTime() {
        TourRequestRequest request = TourRequestRequest.builder()
                .tourDate(LocalDate.now().plusDays(1))
                .tourTime(LocalTime.of(10, 15))
                .advertId(1L)
                .build();

        when(messageUtil.getMessage("error.tour-time.bad-request")).thenReturn("invalid time");

        assertThatThrownBy(() -> service.save(request, new User()))
                .isInstanceOf(RelatedFieldException.class);
    }

    @Test
    void save_throwsWhenOwnerCreatesRequest() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        Advert advert = new Advert();
        advert.setId(2L);
        advert.setUser(user);

        TourRequestRequest request = TourRequestRequest.builder()
                .tourDate(LocalDate.now().plusDays(1))
                .tourTime(LocalTime.of(10, 0))
                .advertId(2L)
                .build();

        when(advertService.getById(2L)).thenReturn(advert);
        when(userService.getUserByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(messageUtil.getMessage("error.tour-request.owner-cannot-create")).thenReturn("owner");

        assertThatThrownBy(() -> service.save(request, user))
                .isInstanceOf(RelatedFieldException.class);
    }
}
