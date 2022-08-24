package com.cryptonita.app.integration.adapters.mappers;

import com.cryptonita.app.dto.integration.CoinHistoricalMarketDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CoinHistoricalInfoMapper implements AdapterMapper<CoinHistoricalMarketDTO> {

    private final ObjectMapper jsonMapper;

    @SneakyThrows
    @Override
    public CoinHistoricalMarketDTO mapToDto(String s) {
        JsonNode json = jsonMapper.readTree(s);
        return innerMap(json);
    }

    @Override
    public List<CoinHistoricalMarketDTO> mapManyToDto(String s) {
        throw new UnsupportedOperationException();
    }

    private CoinHistoricalMarketDTO innerMap(JsonNode json) {
        return CoinHistoricalMarketDTO.builder()
                .id(json.get("id").asText())
                .symbol(json.get("symbol").asText())
                .name(json.get("name").asText())
                .price(retrieveCurrentPrice(json))
                .market_cap(retrieveMarketCap(json))
                .total_volume(retrieveTotalVolume(json))
                .build();
    }

    private double retrieveCurrentPrice(JsonNode json)  {
        JsonNode marketData = json.get("market_data");
        JsonNode currentPrice = marketData.get("current_price");

        return currentPrice.get("usd").asDouble();
    }

    private double retrieveMarketCap(JsonNode json)  {
        JsonNode marketData = json.get("market_data");
        JsonNode marketCap = marketData.get("market_cap");

        return marketCap.get("usd").asDouble();
    }

    private double retrieveTotalVolume(JsonNode json)  {
        JsonNode marketData = json.get("market_data");
        JsonNode totalVolume = marketData.get("total_volume");

        return totalVolume.get("usd").asDouble();
    }

}
