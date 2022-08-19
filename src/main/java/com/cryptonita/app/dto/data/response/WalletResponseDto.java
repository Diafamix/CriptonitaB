package com.cryptonita.app.dto.data.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WalletResponseDto {

    private long id;
    private String coinName;
    private double quantity;

}
