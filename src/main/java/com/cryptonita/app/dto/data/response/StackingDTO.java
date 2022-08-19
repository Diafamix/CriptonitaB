package com.cryptonita.app.dto.data.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class StackingDTO {

    private long id;
    private CoinResponseDTO coin;
    private UserResponseDTO user;
    private double quantity;
    private LocalDateTime createdAt;
    private int daysToExpire;
}
