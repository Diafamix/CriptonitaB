package com.cryptonita.app.core.controllers.services;

import com.cryptonita.app.dto.data.response.FavoritesResponseDto;

import java.util.List;

public interface IFavoritesService {
    List<FavoritesResponseDto> getByName();

    FavoritesResponseDto delete(String coin);

    FavoritesResponseDto create(String coin);

}
