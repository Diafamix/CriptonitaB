package com.cryptonita.app.integration.adapters.impl;

import com.cryptonita.app.dto.integration.CoinMarketDTO;
import com.cryptonita.app.integration.adapters.ICoinMarketAdapter;
import com.cryptonita.app.integration.adapters.mappers.AdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Service
@AllArgsConstructor
public class CoinMarketAdapterImpl implements ICoinMarketAdapter {

    private static final String URL = "https://api.coingecko.com/api/v3/coins/markets";

    private final WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(
                    HttpClient.create().followRedirect(true)
            ))
            .baseUrl(URL)
            .build();

    private final AdapterMapper<CoinMarketDTO> mapper;

    @Override
    public Flux<CoinMarketDTO> getManyCoins(String vs_currency) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .queryParam("vs_currency",vs_currency)
                                .build()
                )
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(s -> Flux.fromStream(mapper.mapManyToDto(s).stream()));
    }


    @Override
    public Flux<CoinMarketDTO> getManyCoinsMetadata(String vs_currency, String ids, String category, String order, Integer per_page,
                                                    Integer page, Boolean sparkline, String price_change_percentage) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .queryParam("vs_currency",vs_currency)
                                .queryParam("ids",ids)
                                .queryParam("category",category)
                                .queryParam("order",order)
                                .queryParam("per_page",per_page)
                                .queryParam("page",page)
                                .queryParam("sparkline",sparkline)
                                .queryParam("price_change_percentage",price_change_percentage)
                                .build()
                )
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(s -> Flux.fromStream(mapper.mapManyToDto(s).stream()));
    }

    @Override
    public Flux<CoinMarketDTO> getManyCoinsByIds(String vs_currency, String ids) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder
                                .queryParam("vs_currency",vs_currency)
                                .queryParam("ids",ids)
                                .build()
                )
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(s -> Flux.fromStream(mapper.mapManyToDto(s).stream()));
    }
}
