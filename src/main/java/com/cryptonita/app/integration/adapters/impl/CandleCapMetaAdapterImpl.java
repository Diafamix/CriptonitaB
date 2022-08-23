package com.cryptonita.app.integration.adapters.impl;

import com.cryptonita.app.dto.integration.CandleInfoDTO;
import com.cryptonita.app.integration.adapters.ICandleCapMetaAdapter;
import com.cryptonita.app.integration.adapters.mappers.AdapterMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

@Service
@AllArgsConstructor
public class CandleCapMetaAdapterImpl implements ICandleCapMetaAdapter {

    private final String URL = "https://api.coingecko.com/api/v3/";

    private final WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(
                    HttpClient.create().followRedirect(true)
            ))
            .baseUrl(URL)
            .build();


    private final AdapterMapper<CandleInfoDTO> mapper;

    @Override
    public Flux<CandleInfoDTO> getCandleOfCoin(String id, String vs_currency, String days) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path(String.format("/coins/%s/ohlc", id))
                                .queryParam("vs_currency", vs_currency)
                                .queryParam("days", days)
                                .build()
                )
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(s -> Flux.fromStream(mapper.mapManyToDto(s).stream()));
    }

}
