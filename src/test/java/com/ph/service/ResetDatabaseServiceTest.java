package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Category;
import com.ph.domain.entities.User;
import com.ph.repository.AdvertRepository;
import com.ph.repository.CategoryRepository;
import com.ph.repository.ContactRepository;
import com.ph.repository.DailyReportRepository;
import com.ph.domain.entities.FavoriteRepository;
import com.ph.domain.entities.TourRequestRepository;
import com.ph.repository.UserRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResetDatabaseServiceTest {

    @Mock
    private AdvertRepository advertRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CacheManager cacheManager;

    @Mock
    private DailyReportRepository dailyReportRepository;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private TourRequestRepository tourRequestRepository;

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ResetDatabaseService resetDatabaseService;

    @Test
    void resetDatabase_deletesNonBuiltInAndClearsCaches() {
        when(userRepository.findAllByBuiltIn(false)).thenReturn(List.of(new User()));
        when(categoryRepository.findAllByBuiltIn(false)).thenReturn(List.of(new Category()));
        when(advertRepository.findAllByBuiltIn(false)).thenReturn(List.of(new Advert()));
        when(cacheManager.getCacheNames()).thenReturn(List.of("one", "two"));
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("one")).thenReturn(cache);
        when(cacheManager.getCache("two")).thenReturn(cache);
        when(messageUtil.getMessage("success.db.reset")).thenReturn("ok");

        resetDatabaseService.resetDatabase();

        verify(userRepository).deleteAll(anyList());
        verify(categoryRepository).deleteAll(anyList());
        verify(advertRepository).deleteAll(anyList());
        verify(dailyReportRepository).deleteAll(anyList());
        verify(tourRequestRepository).deleteAll(anyList());
        verify(favoriteRepository).deleteAll(anyList());
        verify(contactRepository).deleteAll(anyList());
        verify(cache, atLeastOnce()).clear();
    }
}
