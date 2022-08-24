package com.cryptonita.app.integration.adapters;

import com.cryptonita.app.dto.integration.CoinHistoricalMarketDTO;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface IHistoricalMarketAdapter {

    Mono<CoinHistoricalMarketDTO> getHistorical(String id, LocalDate dateAt);

}
