package com.cryptonita.app.integration.adapters.mappers;

import com.cryptonita.app.dto.integration.ConversorDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class ConversorMapper implements AdapterMapper<ConversorDTO> {

    private final ObjectMapper jsonParser;

    @SneakyThrows
    @Override
    public ConversorDTO mapToDto(String s) {
        JsonNode json = jsonParser.readTree(s);
        ArrayNode data = (ArrayNode) json.get("data");

        JsonNode first = data.iterator().next();

        return innerMap(first);
    }

    @Override
    public List<ConversorDTO> mapManyToDto(String s) {
        throw new UnsupportedOperationException();
    }

    private ConversorDTO innerMap(JsonNode node) {
        TuplePrice convertedPrice = innerQuoteMap(node.get("quote"));

        return ConversorDTO.builder()
                .from(node.get("symbol").asText())
                .to(convertedPrice.coin)
                .amount(node.get("amount").asDouble())
                .price(convertedPrice.price)
                .time(LocalDateTime.now().toLocalDate()) // TODO .parse(node.get("last_updated").asText()).toLocalDate()
                .build();
    }

    private TuplePrice innerQuoteMap(JsonNode node) {
        Map.Entry<String, JsonNode> quoteNode = node.fields().next();

        return TuplePrice.of(quoteNode.getKey(), quoteNode.getValue().get("price").asDouble());
    }

    @AllArgsConstructor
    private static final class TuplePrice {

        public static TuplePrice of(String coin, double price) {
            return new TuplePrice(coin, price);
        }

        public final String coin;
        public final double price;

    }

}
