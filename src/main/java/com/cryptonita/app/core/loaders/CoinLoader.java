package com.cryptonita.app.core.loaders;

import com.cryptonita.app.data.providers.ICoinProvider;
import com.cryptonita.app.dto.integration.CoinMarketDTO;
import com.cryptonita.app.integration.services.ICoinIntegrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class CoinLoader {

    private final ICoinProvider coinProvider;
    private final ICoinIntegrationService coinService;

    /**
     * Retrieves all the coins info from an external api and saves
     * them on the database
     *
     * @return the flux that saves the coins
     */
    public Flux<CoinMarketDTO> load() {
        return coinService.getAllMarkets()
                .doOnNext(marketDTO -> {
                            coinProvider.createCoin(
                                    marketDTO.getId(),
                                    marketDTO.getName(),
                                    marketDTO.getSymbol().toUpperCase());
                        }
                );

    }

}
