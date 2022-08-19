package com.cryptonita.app.dto.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CoinResponseDTO {

    public long id;
    public String coinID;
    public String name;
    public String symbol;
}
