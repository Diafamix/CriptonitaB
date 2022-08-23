package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IFavoritesService;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.response.FavoritesResponseDto;
import com.cryptonita.app.security.SecurityContextHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoritesServiceImpl implements IFavoritesService {

    private final IUserProvider userProvider;
    private final SecurityContextHelper securityContextHelper;

    @Override
    public List<FavoritesResponseDto> getByName() {

        return securityContextHelper.getUser().getFavorites();
    }

    @Override
    public FavoritesResponseDto delete(String coin) {

        return userProvider.removeFavorite(securityContextHelper.getUser().getUsername(), coin);
    }

    @Override
    public FavoritesResponseDto create(String coin) {

        return userProvider.addFavourite(securityContextHelper.getUser().getUsername(), coin);
    }
}
