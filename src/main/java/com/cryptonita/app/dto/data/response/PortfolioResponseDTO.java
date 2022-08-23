package com.cryptonita.app.dto.data.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PortfolioResponseDTO {

    private final List<CoinDetailsDTO> wallets;
    private double balance;

}
