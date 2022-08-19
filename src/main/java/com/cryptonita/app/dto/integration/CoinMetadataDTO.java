package com.cryptonita.app.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Builder
@ToString
public class CoinMetadataDTO {

    public final String id;
    public final String symbol;
    public final String name;
    public final int rank;
    public final double block_time_in_minutes;
    public final String hashing_algorithm;
    public final List<String> categories;
    public final String description;
    public final String image;
    public final String homepage;
    public final String sourceCode;
    public final LocalDateTime genesis_date;
    public final double sentiment_votes_up_percentage;
    public final double sentiment_votes_down_percentage;
    public final double developer_score;
    public final double community_score;
    public final double liquidity_score;
    public final double public_interest_score;
    public final List<CoinInfoStatusDTO> status;

    @AllArgsConstructor
    @Builder
    @ToString
    public final static class CoinInfoStatusDTO {
        public final String title;
        public final String description;
        public final String category;
        public final String user;
        public final LocalDateTime createAt;
    }

}
