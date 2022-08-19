package com.cryptonita.app.integration.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CoinCapJsonParser {

    private final ObjectMapper mapper;

    @SneakyThrows
    public List<CoinUpdatePair> parse(String s) {
        List<CoinUpdatePair> coinUpdatePairs = new ArrayList<>();
        JsonNode json = mapper.readTree(s);

        json.fields().forEachRemaining(entry -> {
            coinUpdatePairs.add(new CoinUpdatePair(entry.getKey(), entry.getValue().asDouble()));
        });

        return coinUpdatePairs;
    }


    @AllArgsConstructor
    @Getter
    public static final class CoinUpdatePair {

        public final String coin;
        public final double price;

    }

}