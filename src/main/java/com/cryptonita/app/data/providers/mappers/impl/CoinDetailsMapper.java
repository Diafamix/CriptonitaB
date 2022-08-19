package com.cryptonita.app.data.providers.mappers.impl;

import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.PorfolioResponseDTO;
import com.cryptonita.app.dto.data.response.WalletResponseDto;

public class CoinDetailsMapper implements IMapper<WalletResponseDto, PorfolioResponseDTO> {



    @Override
    public PorfolioResponseDTO mapToDto(WalletResponseDto walletResponseDto) {
        return null;
    }

    @Override
    public WalletResponseDto mapToEntity(PorfolioResponseDTO porfolioResponseDTO) {
        return null;
    }
}
