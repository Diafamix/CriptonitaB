package com.cryptonita.app.integration.adapters.mappers;

import com.cryptonita.app.dto.integration.CoinMetadataDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CoinInfoMapper implements AdapterMapper<CoinMetadataDTO> {

    private final ObjectMapper jsonMapper;

    @SneakyThrows
    @Override
    public CoinMetadataDTO mapToDto(String s) {
        JsonNode json = jsonMapper.readTree(s);

        return CoinMetadataDTO.builder()
                .id(json.get("id").asText())
                .symbol(json.get("symbol").asText())
                .name(json.get("name").asText())
                .rank(json.get("market_cap_rank").asInt())
                .block_time_in_minutes(json.get("block_time_in_minutes").asDouble())
                .hashing_algorithm(json.get("hashing_algorithm").asText())
                .categories(getCategories(json))
                .description(json.get("description").get("en").asText())
                .image(json.get("image").get("small").asText())
                .homepage(getHomePage(json))
                .sourceCode(getSourceCode(json))
                .genesis_date(LocalDateTime.now()) // TODO
                .sentiment_votes_down_percentage(json.get("sentiment_votes_down_percentage").asDouble())
                .sentiment_votes_up_percentage(json.get("sentiment_votes_up_percentage").asDouble())
                .developer_score(json.get("developer_score").asDouble())
                .community_score(json.get("community_score").asDouble())
                .liquidity_score(json.get("liquidity_score").asDouble())
                .public_interest_score(json.get("public_interest_score").asDouble())
                .status(getStatus(json))
                .build();
    }

    @Override
    public List<CoinMetadataDTO> mapManyToDto(String s) {
        throw new UnsupportedOperationException();
    }

    private List<String> getCategories(JsonNode node) {
        JsonNode categories = node.get("categories");

        List<String> categoriesList = new ArrayList<>(categories.size());
        categories.forEach(jsonNode -> categoriesList.add(jsonNode.asText()));

        return categoriesList;
    }

    private String getHomePage(JsonNode node) {
        JsonNode links = node.get("links");
        ArrayNode homepage = (ArrayNode) links.get("homepage");

        return homepage.get(0).asText();  // Get the first link
    }

    private String getSourceCode(JsonNode node) {
        JsonNode links = node.get("links");
        JsonNode repos = links.get("repos_url");
        JsonNode github = repos.get("github");

        return github.iterator().next().asText();
    }

    private List<CoinMetadataDTO.CoinInfoStatusDTO> getStatus(JsonNode node) {
        ArrayNode status = (ArrayNode) node.get("status_updates");

        List<CoinMetadataDTO.CoinInfoStatusDTO> statuses = new ArrayList<>();
        status.forEach(jsonNode -> statuses.add(mapStatus(jsonNode)));

        return statuses;
    }

    private CoinMetadataDTO.CoinInfoStatusDTO mapStatus(JsonNode node) {
        return CoinMetadataDTO.CoinInfoStatusDTO.builder()
                .title(node.get("user_title").asText())
                .description(node.get("description").asText())
                .category(node.get("category").asText())
                .user(node.get("user").asText())
                .createAt(LocalDateTime.now()) // TODO node.get("created_at").asText()
                .build();
    }

}
