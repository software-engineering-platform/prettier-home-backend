package com.ph.controller;

import com.ph.payload.response.AdvertResponseForFavorite;
import com.ph.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;

    // Not :K01 - GetFavoritesByCustomer() ***************************************************
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")//http://localhost:8080/favorites/auth
    @GetMapping("/auth")
    public ResponseEntity<List<AdvertResponseForFavorite>> getFavoritesByCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        return favoritesService.getFavoritesByCustomer(userDetails);
    }

    // Not :K02 - GetByUserIdFavoritesByManagerAndAdmin() ***************************************************
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")//http://localhost:8080/favorites/admin/1
    @GetMapping("/admin/{id}")
    public ResponseEntity<List<AdvertResponseForFavorite>> getByUserIdFavoritesForManagerAndAdmin(@PathVariable(name = "id") Long id) {
        return favoritesService.getByUserIdFavoritesForManagerAndAdmin(id);
    }

    // Not :K03 - FavoriteSave() ***************************************************************************
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")//http://localhost:8080/favorites/1/auth
    @PostMapping("{id}/auth")
    public ResponseEntity<AdvertResponseForFavorite> addToFavorites(
            @PathVariable(name = "id") Long advertId, @AuthenticationPrincipal UserDetails userDetails) {
        return favoritesService.addToFavorites(advertId, userDetails);
    }

    // Not :K04 - DeleteFavoriteByCustomer() *****************************************************************
    @PreAuthorize("hasAnyAuthority('CUSTOMER','MANAGER','ADMIN')")//http://localhost:8080/favorites/auth
    @DeleteMapping("/auth")
    public ResponseEntity<String> deleteFavoriteByCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        return favoritesService.deleteFavoriteByCustomer(userDetails);
    }

    // Not :K05 - DeleteFavoriteIdByAdminAndManager() *********************************************************
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")//http://localhost:8080/favorites/1/admin TODO: Endpoint'i kontrol edilecek
    @DeleteMapping("/{id}/admin")
    public ResponseEntity<String> deleteFavoritesByAdminAndManager(@PathVariable(name = "id") Long userId) {
        return favoritesService.deleteFavoritesByAdminAndManager(userId);
    }

    // Not :K06 - DeleteFavoriteIdByAdminAndManager() *********************************************************
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")//http://localhost:8080/favorites/1/admin/favorite
    @DeleteMapping("/{id}/admin/favorite")
    public ResponseEntity<String> deleteFavoriteIdByAdminAndManager(@PathVariable(name = "id") Long favoriteId) {
        return favoritesService.deleteFavoriteIdByAdminAndManager(favoriteId);
    }
}