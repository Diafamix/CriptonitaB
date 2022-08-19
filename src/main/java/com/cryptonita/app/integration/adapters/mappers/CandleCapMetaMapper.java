package com.cryptonita.app.integration.adapters.mappers;

import com.cryptonita.app.dto.integration.CandleInfoDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
public class CandleCapMetaMapper implements AdapterMapper<CandleInfoDTO>{

    private final ObjectMapper jsonMapper;

    @SneakyThrows
    @Override
    public CandleInfoDTO mapToDto(String s) {
        JsonNode jsonNode = jsonMapper.readTree(s);
        ArrayNode data = (ArrayNode) jsonNode.get(1);

        return mapper(data.iterator().next());
    }

    @SneakyThrows
    @Override
    public List<CandleInfoDTO> mapManyToDto(String s) {
        JsonNode jsonNode = jsonMapper.readTree(s);
        ArrayNode data = (ArrayNode) jsonNode;

        List<CandleInfoDTO> candles = new ArrayList<>();
        data.forEach(node -> candles.add(mapper(node)));


        return candles;
    }

    private CandleInfoDTO mapper(JsonNode jsonNode) {

        return CandleInfoDTO.builder()
                .time(jsonNode.get(0).asDouble())
                .open(jsonNode.get(1).asDouble())
                .high(jsonNode.get(2).asDouble())
                .low(jsonNode.get(3).asDouble())
                .close(jsonNode.get(4).asDouble())
                .build();
    }
}
