package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IConvertorService;
import com.cryptonita.app.core.controllers.services.IPortfolioService;
import com.cryptonita.app.data.providers.IAccountProvider;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.PorfolioResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.dto.data.response.WalletResponseDto;
import com.cryptonita.app.integration.services.ICoinIntegrationService;
import com.cryptonita.app.security.SecurityContextHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class PortfolioServiceImpl implements IPortfolioService {

    private IUserProvider userProvider;
    private final IAccountProvider acountProvider;
    private final ICoinIntegrationService coinIntegrationService;
    private final SecurityContextHelper securityContextHelper;

    private final IConvertorService convertorService;
    private final IMapper<Map<String,WalletResponseDto>, PorfolioResponseDTO> mapper;

    @Override
    public WalletResponseDto get(String coin) {
        return acountProvider.get(securityContextHelper.getUser().getUsername(),coin);
    }

    @Override
    public PorfolioResponseDTO getAll() {
        UserResponseDTO userResponseDTO = securityContextHelper.getUser();
        Map<String,WalletResponseDto> wallets = userResponseDTO.getWallet();

        return mapper.mapToDto(wallets);
    }



}
