package com.cryptonita.app.core.controllers.services;

import com.cryptonita.app.dto.data.response.PortfolioResponseDTO;
import com.cryptonita.app.dto.data.response.WalletResponseDto;

/**
 * This class represents all the relevant methods to get information about User and Account from the database.
 */
public interface IPortfolioService {
    WalletResponseDto get(String coin);

    PortfolioResponseDTO getAll();

}
