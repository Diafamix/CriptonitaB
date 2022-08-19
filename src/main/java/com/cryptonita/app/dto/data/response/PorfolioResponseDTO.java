package com.cryptonita.app.dto.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Builder
@Data
public class PorfolioResponseDTO {

    private double balance ;
    private final List<CoinDetailsDTO> coinDetailsDTOList;




}
