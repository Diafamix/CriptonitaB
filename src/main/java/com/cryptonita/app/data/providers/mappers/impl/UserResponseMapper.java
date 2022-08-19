package com.cryptonita.app.data.providers.mappers.impl;

import com.cryptonita.app.data.entities.FavouritesModel;
import com.cryptonita.app.data.entities.UserModel;
import com.cryptonita.app.data.entities.WalletModel;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.FavoritesResponseDto;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.dto.data.response.WalletResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserResponseMapper implements IMapper<UserModel, UserResponseDTO> {

    private final IMapper<FavouritesModel, FavoritesResponseDto> favouritesMapper;
    private final IMapper<WalletModel, WalletResponseDto> walletMapper;

    @Override
    public UserResponseDTO mapToDto(UserModel userModel) {
        Map<String, WalletResponseDto> walletMap = new HashMap<>();

        userModel.getWallets().forEach(walletModel ->
                walletMap.put(walletModel.getCoin().getCoinID(), walletMapper.mapToDto(walletModel))
        );

        List<FavoritesResponseDto> favoritesResponseDtos = userModel.getFavourites().stream()
                .map(favouritesMapper::mapToDto)
                .collect(Collectors.toList());

        return UserResponseDTO.builder()
                .mail(userModel.getMail())
                .username(userModel.getUsername())
                .role(userModel.getRole())
                .type(userModel.getType())
                .favorites(favoritesResponseDtos)
                .wallet(walletMap)
                .build();
    }

    @Override
    public UserModel mapToEntity(UserResponseDTO userResponseDTO) {
        throw new UnsupportedOperationException();
    }
}
