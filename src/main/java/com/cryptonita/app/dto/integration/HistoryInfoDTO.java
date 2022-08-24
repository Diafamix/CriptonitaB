package com.cryptonita.app.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@AllArgsConstructor
@Builder
@ToString
public class HistoryInfoDTO {

    public final double priceUsd;
    public final long time;

}
