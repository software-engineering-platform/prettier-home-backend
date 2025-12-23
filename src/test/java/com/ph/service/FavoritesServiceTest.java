package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Favorite;
import com.ph.domain.entities.User;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.AdvertMapper;
import com.ph.repository.FavoritesRepository;
import com.ph.utils.MessageUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoritesServiceTest {

    @Mock
    private FavoritesRepository favoritesRepository;

    @Mock
    private UserService userService;

    @Mock
    private MessageUtil messageUtil;

    @Mock
    private AdvertService advertService;

    @Mock
    private AdvertMapper advertMapper;

    @InjectMocks
    private FavoritesService favoritesService;

    @Test
    void addToFavorites_savesWhenMissing() {
        User user = new User();
        user.setId(1L);
        Advert advert = new Advert();
        advert.setId(10L);

        when(favoritesRepository.findByUser_IdAndAdvert_Id(1L, 10L)).thenReturn(Optional.empty());
        when(advertService.getById(10L)).thenReturn(advert);
        when(favoritesRepository.save(any(Favorite.class))).thenReturn(Favorite.builder().id(2L).advert(advert).build());

        favoritesService.addToFavorites(10L, user);

        verify(favoritesRepository).save(any(Favorite.class));
    }

    @Test
    void deleteFavorite_throwsWhenMissing() {
        when(favoritesRepository.findById(99L)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("error.favorite.not.found")).thenReturn("missing");

        assertThatThrownBy(() -> favoritesService.deleteFavorite(99L, new User()))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
