package com.cryptonita.app.core.controllers.mapper;

import com.cryptonita.app.core.utils.MapperFactoryService;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.controller.CoinDTO;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import com.cryptonita.app.dto.integration.CoinMarketDTO;
import com.cryptonita.app.integration.services.ICoinIntegrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CoinWithMetaMapper implements IMapper<CoinResponseDTO, Mono<CoinDTO>> {

    private final ICoinIntegrationService coinService;

    @Override
    public Mono<CoinDTO> mapToDto(CoinResponseDTO coinResponseDTO) {
        return coinService.getAllMarketByIds(coinResponseDTO.coinID)
                .single()
                .map(coinMetadataDTO -> mergeCoinAndMetadata(coinResponseDTO, coinMetadataDTO));
    }

    @Override
    public CoinResponseDTO mapToEntity(Mono<CoinDTO> coinDtoMono) {
        throw new UnsupportedOperationException();
    }

    private CoinDTO mergeCoinAndMetadata(CoinResponseDTO responseDTO, CoinMarketDTO marketDTO) {
        CoinDTO.CoinMarketDTO marketControllerDTO = new CoinDTO.CoinMarketDTO();
        MapperFactoryService.doMap(marketDTO, marketControllerDTO);

        return CoinDTO.builder()
                .id(responseDTO.coinID)
                .name(responseDTO.name)
                .symbol(responseDTO.symbol)
                .rank(responseDTO.id)
                .marketData(marketControllerDTO)
                .build();
    }

}
