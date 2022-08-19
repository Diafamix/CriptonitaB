package com.cryptonita.app.dto.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SwapUsersResponseDto {

    private String userFrom;
    private String userTo;
    private String walletFrom;
    private String walletTo;
    private double amountFrom;
    private double amountTo;

}
