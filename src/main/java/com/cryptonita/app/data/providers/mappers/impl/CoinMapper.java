package com.cryptonita.app.data.providers.mappers.impl;

import com.cryptonita.app.data.entities.CoinModel;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CoinMapper implements IMapper<CoinModel, CoinResponseDTO> {

    @Override
    public CoinResponseDTO mapToDto(CoinModel coinModel) {
       return CoinResponseDTO.builder()
               .id(coinModel.getId())
               .coinID(coinModel.getCoinID())
               .name(coinModel.getName())
               .symbol(coinModel.getSymbol())
               .build();
    }

    @Override
    public CoinModel mapToEntity(CoinResponseDTO coinResponseDTO) {
        throw new UnsupportedOperationException();
    }
}
