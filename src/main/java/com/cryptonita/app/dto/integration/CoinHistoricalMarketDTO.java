package com.cryptonita.app.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
public class CoinHistoricalMarketDTO {

    public final String id;
    public final String symbol;
    public final String name;
    public final double price;
    public final double market_cap;
    public final double total_volume;

}
