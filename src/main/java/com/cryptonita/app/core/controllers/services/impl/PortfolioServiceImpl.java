package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IPortfolioService;
import com.cryptonita.app.core.services.IPortfolioCalculator;
import com.cryptonita.app.data.providers.IAccountProvider;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.PortfolioResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.dto.data.response.WalletResponseDto;
import com.cryptonita.app.dto.integration.HistoryInfoDTO;
import com.cryptonita.app.security.SecurityContextHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PortfolioServiceImpl implements IPortfolioService {

    private final IAccountProvider accountProvider;
    private final SecurityContextHelper securityContextHelper;
    private final IPortfolioCalculator portfolioCalculator;

    private final IMapper<Map<String, WalletResponseDto>, PortfolioResponseDTO> mapper;

    @Override
    public WalletResponseDto get(String coin) {
        return accountProvider.get(securityContextHelper.getUser().getUsername(), coin);
    }

    @Override
    public PortfolioResponseDTO getAll() {
        UserResponseDTO userResponseDTO = securityContextHelper.getUser();
        Map<String, WalletResponseDto> wallets = userResponseDTO.getWallet();

        return mapper.mapToDto(wallets);
    }

    @Override
    public List<HistoryInfoDTO> getPortfolioChart() {
        return portfolioCalculator.calculateAllTime(securityContextHelper.getUser().username);
    }


}
