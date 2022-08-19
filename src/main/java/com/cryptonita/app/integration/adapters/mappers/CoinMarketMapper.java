package com.cryptonita.app.integration.adapters.mappers;

import com.cryptonita.app.dto.integration.CoinMarketDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class CoinMarketMapper implements AdapterMapper<CoinMarketDTO>{

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public CoinMarketDTO mapToDto(String s) {
        ArrayNode json = (ArrayNode) objectMapper.readTree(s);
        JsonNode data = json.iterator().next();

        return innerMap(data);
    }

    @SneakyThrows
    @Override
    public List<CoinMarketDTO> mapManyToDto(String s) {
        ArrayNode json = (ArrayNode) objectMapper.readTree(s);
        List<CoinMarketDTO> list = new ArrayList<>();

        for (JsonNode jsonNode : json) {
            list.add(innerMap(jsonNode));
        }

        return list;
    }

    private CoinMarketDTO innerMap(JsonNode data) {
        return CoinMarketDTO.builder()
                .id(data.get("id").asText())
                .symbol(data.get("symbol").asText())
                .name(data.get("name").asText())
                .image(data.get("image").asText())
                .current_price(data.get("current_price").asDouble())
                .market_cap(data.get("market_cap").asDouble())
                .market_cap_rank(data.get("market_cap_rank").asInt())
                .fully_diluted_valuation(data.get("fully_diluted_valuation").asDouble())
                .total_volume(data.get("total_volume").asDouble())
                .high_24h(data.get("high_24h").asDouble())
                .low_24h(data.get("low_24h").asDouble())
                .price_change_24h(data.get("price_change_24h").asDouble())
                .price_change_percentage_24h(data.get("price_change_percentage_24h").asDouble())
                .market_cap_change_24h(data.get("market_cap_change_24h").asDouble())
                .market_cap_change_percentage_24h(data.get("market_cap_change_percentage_24h").asDouble())
                .circulating_supply(data.get("circulating_supply").asDouble())
                .total_supply(data.get("total_supply").asDouble())
                .max_supply(data.get("max_supply").asDouble())
                .ath(data.get("ath").asDouble())
                .ath_change_percentage(data.get("ath_change_percentage").asDouble())
                .ath_date(data.get("ath_date").asText())
                .atl(data.get("atl").asDouble())
                .atl_change_percentage(data.get("atl_change_percentage").asDouble())
                .atl_date(data.get("atl_date").asText())
                .roi(data.get("roi").asText())
                .last_updated(data.get("last_updated").asText())
                .build();
    }


}
