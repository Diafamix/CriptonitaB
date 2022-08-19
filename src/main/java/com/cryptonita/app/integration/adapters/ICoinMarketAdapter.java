package com.cryptonita.app.integration.adapters;

import com.cryptonita.app.dto.integration.CoinMarketDTO;
import reactor.core.publisher.Flux;

public interface ICoinMarketAdapter {

    default Flux<CoinMarketDTO> getManyCoins() {
        return getManyCoins("usd");
    }

    Flux<CoinMarketDTO> getManyCoins(String vs_currency);

    Flux<CoinMarketDTO> getManyCoinsMetadata(String vs_currency, String ids, String category, String order, Integer per_page,
                                             Integer page, Boolean sparkline, String price_change_percentage);

    default Flux<CoinMarketDTO> getManyCoinsByIds(String vs_currency, String... ids) {
        return getManyCoinsByIds(vs_currency, String.join(",", ids));
    }

    Flux<CoinMarketDTO> getManyCoinsByIds(String vs_currency, String ids);

    default Flux<CoinMarketDTO> getManyCoinsByIds(String ids) {
        return getManyCoinsByIds("usd", ids);
    }

    default Flux<CoinMarketDTO> getManyCoinsByIds(String... ids) {
        return getManyCoinsByIds("usd", ids);
    }


}
