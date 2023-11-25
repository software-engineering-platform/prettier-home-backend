package com.ph.service;

import com.ph.domain.entities.Advert;
import com.ph.domain.entities.Favorite;
import com.ph.domain.entities.User;
import com.ph.exception.customs.ResourceNotFoundException;
import com.ph.payload.mapper.AdvertMapper;
import com.ph.payload.response.AdvertResponseForFavorite;
import com.ph.repository.FavoritesRepository;
import com.ph.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {
    private final FavoritesRepository favoritesRepository;
    private final UserService userService;
    private final MessageUtil messageUtil;
    private final AdvertService advertService;
    private final AdvertMapper advertMapper;

    // Not :K01 - GetFavoritesByCustomer() ***************************************************
    public ResponseEntity<List<AdvertResponseForFavorite>> getFavoritesByCustomer(UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername()).orElseThrow(() ->
                new ResourceNotFoundException(messageUtil.getMessage("error.user.not-found.id")));
        List<Favorite> favorites = favoritesRepository.findByUser_Id(user.getId());
        List<Advert> adverts = favorites.stream().map(Favorite::getAdvert).toList();
        return ResponseEntity.ok(adverts.stream().map(advertMapper::toAdvertResponseForFavorite).collect(Collectors.toList()));
    }

    // Not :K02 - GetByUserIdFavoritesByManagerAndAdmin() ***************************************************
    public ResponseEntity<List<AdvertResponseForFavorite>>getByUserIdFavoritesForManagerAndAdmin(Long id) {
        List<Favorite> favorites = favoritesRepository.findByUser_Id(id);
        List<Advert> adverts = favorites.stream().map(Favorite::getAdvert).toList();
        return ResponseEntity.ok(adverts.stream().map(advertMapper::toAdvertResponseForFavorite).collect(Collectors.toList()));
    }


    // Not :K03 - FavoriteSave() ***************************************************************************
    public ResponseEntity<AdvertResponseForFavorite> addToFavorites(Long advertId, UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.user.not-found.id")));

        Optional<Favorite> favorite = favoritesRepository.findByUser_IdAndAdvert_Id(user.getId(), advertId);

        if (favorite.isPresent()) {
            favoritesRepository.delete(favorite.get());
            return ResponseEntity.ok().body(advertMapper.toAdvertResponseForFavorite(favorite.get().getAdvert()));
        } else {

            Advert advert = advertService.getById(advertId);
            Favorite newFavorite = new Favorite();
            newFavorite.setUser(user);
            newFavorite.setAdvert(advert);

            Favorite savedFavorite = favoritesRepository.save(newFavorite);

            return ResponseEntity.ok(advertMapper.toAdvertResponseForFavorite(savedFavorite.getAdvert()));
        }
    }

    // Not :K04 - DeleteFavoriteByCustomer() ***************************************************************************
    public ResponseEntity<String> deleteFavoriteByCustomer(UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(messageUtil.getMessage("error.user.not-found.id")));
        favoritesRepository.deleteAllById(user.getFavorites().stream().map(Favorite::getId).collect(Collectors.toList()));
        return ResponseEntity.ok().body(messageUtil.getMessage("success.favorites.successfully.deleted"));
    }

    // Not :K05 - DeleteFavoriteIdByAdminAndManager() *********************************************************
    public ResponseEntity<String> deleteFavoritesByAdminAndManager(Long userId) {
        User user=userService.getOneUserById(userId);
        favoritesRepository.deleteAllById(user.getFavorites().stream().map(Favorite::getId).collect(Collectors.toList()));
        return ResponseEntity.ok().body(messageUtil.getMessage("success.favorites.successfully.deleted"));
    }

    // Not :K06 - DeleteFavoriteIdByAdminAndManager() *********************************************************
    public ResponseEntity<String> deleteFavoriteIdByAdminAndManager(Long favoriteId) {
        favoritesRepository.deleteById(favoriteId);
        return ResponseEntity.ok().body(messageUtil.getMessage("success.favorite.successfully.deleted"));
    }



}

