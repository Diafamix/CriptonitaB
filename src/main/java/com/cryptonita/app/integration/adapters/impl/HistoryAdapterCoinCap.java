package com.cryptonita.app.integration.adapters.impl;

import com.cryptonita.app.dto.integration.HistoryInfoDTO;
import com.cryptonita.app.integration.adapters.IHistoryAdapter;
import com.cryptonita.app.integration.adapters.mappers.AdapterMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

@Service
@AllArgsConstructor
public class HistoryAdapterCoinCap implements IHistoryAdapter {

    private static final String URL = "https://api.coingecko.com/api/v3/";

    private final WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(
                    HttpClient.create().followRedirect(true)
            ))
            .baseUrl(URL)
            .build();

    private final AdapterMapper<HistoryInfoDTO> mapper;

    @Override
    public Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days, String interval) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(String.format("/coins/%s/market_chart", id))
                                .queryParam("vs_currency", vs_currency)
                                .queryParam("days", days)
                                .queryParam("interval", interval)
                                .build()

                )
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(s -> Flux.fromStream(mapper.mapManyToDto(s).stream()));
    }

    @Override
    public Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(String.format("/coins/%s/market_chart", id))
                                .queryParam("vs_currency", vs_currency)
                                .queryParam("days", days)
                                .build()
                )
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(s -> Flux.fromStream(mapper.mapManyToDto(s).stream()));

    }
}
