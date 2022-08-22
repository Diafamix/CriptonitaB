package com.cryptonita.app.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
public final class CoinDTO {

    public final String id;
    public final String name;
    public final String symbol;
    public final long rank;
    public final CoinMarketDTO marketData;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static final class CoinMarketDTO {
        public String image;
        public double current_price;
        public double market_cap;
        public int market_cap_rank;
        public double fully_diluted_valuation;
        public double total_volume;
        public double high_24h;
        public double low_24h;
        public double price_change_24h;
        public double price_change_percentage_24h;
        public double market_cap_change_24h;
        public double market_cap_change_percentage_24h;
        public double circulating_supply;
        public double total_supply;
        public double max_supply;
        public double ath;
        public double ath_change_percentage;
        public String ath_date;
        public double atl;
        public double atl_change_percentage;
        public String atl_date;
        public String roi;
        public String last_updated;
    }

}
