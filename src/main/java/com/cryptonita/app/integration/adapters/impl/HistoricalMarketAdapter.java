package com.cryptonita.app.integration.adapters.impl;

import com.cryptonita.app.dto.integration.CoinHistoricalMarketDTO;
import com.cryptonita.app.integration.adapters.IHistoricalMarketAdapter;
import com.cryptonita.app.integration.adapters.mappers.AdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class HistoricalMarketAdapter implements IHistoricalMarketAdapter {

    private static final String COIN_MARKET_URL = "https://api.coingecko.com/api/v3/coins/{id}/history";
    private static final DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final WebClient coinMarketClient = WebClient.builder()
            .baseUrl(COIN_MARKET_URL)
            .build();

    private final AdapterMapper<CoinHistoricalMarketDTO> mapper;

    @Override
    public Mono<CoinHistoricalMarketDTO> getHistorical(String id, LocalDate dateAt) {
        return coinMarketClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("date", getDateFormatted(dateAt))
                        .build(id)
                )
                .retrieve()
                .bodyToMono(String.class)
                .map(mapper::mapToDto);

    }

    private String getDateFormatted(LocalDate date) {
        return date.format(date_formatter);
    }

}
