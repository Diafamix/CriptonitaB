package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IAssetsService;
import com.cryptonita.app.data.providers.ICoinProvider;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.controller.CoinDTO;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import com.cryptonita.app.dto.integration.CandleInfoDTO;
import com.cryptonita.app.dto.integration.CoinMetadataDTO;
import com.cryptonita.app.dto.integration.HistoryInfoDTO;
import com.cryptonita.app.integration.services.ICoinIntegrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AssetsServiceImpl implements IAssetsService {

    private final ICoinProvider coinProvider;

    private final ICoinIntegrationService coinService;

    private final IMapper<CoinResponseDTO, Mono<CoinDTO>> coinDTOMapper;
    private final IMapper<List<CoinResponseDTO>, Flux<CoinDTO>> coinDTOManyMapper;

    @Override
    public List<CoinResponseDTO> list() {
        return coinProvider.getAllCoins();
    }

    @Override
    public Mono<CoinMetadataDTO> getMetadata(String coinID) {
        return coinService.getInfo(coinID);
    }

    @Override
    public Flux<CoinMetadataDTO> getMetadata(String... coinIDs) {
        return coinService.getAllInfos(coinIDs);
    }

    @Override
    public Flux<CoinDTO> getAll() {
        return coinDTOManyMapper.mapToDto(coinProvider.getAllCoins());
    }

    @Override
    public Flux<CoinDTO> getAll(String ids) {
        return coinDTOManyMapper.mapToDto(coinProvider.getCoins(ids));
    }

    @Override
    public Mono<CoinDTO> getById(String coinID) {
        return coinDTOMapper.mapToDto(coinProvider.getCoinById(coinID));
    }

    @Override
    public Mono<CoinDTO> getByRank(int rank) {
        return coinDTOMapper.mapToDto(coinProvider.getByRank(rank));
    }

    @Override
    public Mono<CoinDTO> getBySymbol(String symbol) {
        return coinDTOMapper.mapToDto(coinProvider.getBySymbol(symbol));
    }

    @Override
    public Mono<CoinDTO> getByName(String name) {
        return coinDTOMapper.mapToDto(coinProvider.getCoinByName(name));
    }

    @Override
    public Flux<HistoryInfoDTO> getAllHistory(String id, String vs_currency, String days, Optional<String> interval) {
        if (!interval.isPresent())
            return coinService.getHistoryOfCoin(id, vs_currency, days);

        return coinService.getHistoryOfCoin(id, vs_currency, days, interval.get());
    }


    @Override
    public Flux<CandleInfoDTO> getAllCandles(String id, String vs_currency, String days) {
        return coinService.getCandleOfCoin(id, vs_currency, days);
    }


}
