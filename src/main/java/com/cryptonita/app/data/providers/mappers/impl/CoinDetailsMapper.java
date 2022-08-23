package com.cryptonita.app.data.providers.mappers.impl;

import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.PortfolioResponseDTO;
import com.cryptonita.app.dto.data.response.WalletResponseDto;

public class CoinDetailsMapper implements IMapper<WalletResponseDto, PortfolioResponseDTO> {


    @Override
    public PortfolioResponseDTO mapToDto(WalletResponseDto walletResponseDto) {
        return null;
    }

    @Override
    public WalletResponseDto mapToEntity(PortfolioResponseDTO portfolioResponseDTO) {
        return null;
    }
}
