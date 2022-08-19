package com.cryptonita.app.dto.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SwapResponseDto {

    private String userName;
    private String walletFrom;
    private String walletTo;
    private double amuountFrom;
    private double amountTo;

}
