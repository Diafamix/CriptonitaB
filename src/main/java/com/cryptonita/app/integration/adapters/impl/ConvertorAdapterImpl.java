package com.cryptonita.app.integration.adapters.impl;

import com.cryptonita.app.dto.integration.ConversorDTO;
import com.cryptonita.app.integration.adapters.IConvertorAdapter;
import com.cryptonita.app.integration.adapters.mappers.AdapterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;

@Component
public class ConvertorAdapterImpl implements IConvertorAdapter {

    private static final String COIN_MARKET_URL = "https://pro-api.coinmarketcap.com";

    @Value("${coinMarketCapApiKey}")
    private String COIN_MARKET_KEY;

    private WebClient coinMarketClient;

    @Autowired
    private AdapterMapper<ConversorDTO> mapper;

    @PostConstruct
    private void load() {
        coinMarketClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                ))
                .baseUrl(COIN_MARKET_URL)
                .build();
    }

    @Override
    public Mono<ConversorDTO> convert(String from, String to, double quantity) {
        return coinMarketClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v2/tools/price-conversion")
                        .queryParam("amount", quantity)
                        .queryParam("symbol", from)
                        .queryParam("convert", to)
                        .build()
                )
                .header("X-CMC_PRO_API_KEY", COIN_MARKET_KEY)
                .retrieve()
                .bodyToMono(String.class)
                .map(mapper::mapToDto);
    }

}
