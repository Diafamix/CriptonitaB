package com.cryptonita.app.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
@Data
public class CoinMarketDTO {

    public String id;
    public String symbol;
    public String name;
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
