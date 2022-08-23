package com.cryptonita.app.dto.data.response;

import com.cryptonita.app.dto.integration.CoinMarketDTO;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CoinDetailsDTO {

    private long id;
    private String name;
    private double quantity;
    private double allocation;
    private CoinMarketDTO marketData;

}
