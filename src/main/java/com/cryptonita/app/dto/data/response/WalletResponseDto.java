package com.cryptonita.app.dto.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WalletResponseDto {

    private long id;
    private String coinName;
    private double quantity;

}
