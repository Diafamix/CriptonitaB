package com.cryptonita.app.core.controllers.services;

import com.cryptonita.app.dto.controller.CoinDTO;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import com.cryptonita.app.dto.integration.CandleInfoDTO;
import com.cryptonita.app.dto.integration.CoinMetadataDTO;
import com.cryptonita.app.dto.integration.HistoryInfoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * This class represents all the relevant methods to get information about User and Account from the database.
 */

public interface IAssetsService {

    List<CoinResponseDTO> list();

    Mono<CoinMetadataDTO> getMetadata(String coinID);

    Flux<CoinMetadataDTO> getMetadata(String... coinIDs);

    Flux<CoinDTO> getAll();

    Flux<CoinDTO> getAll(String ids);

    Mono<CoinDTO> getById(String coinID);

    Mono<CoinDTO> getByRank(int rank);

    Mono<CoinDTO> getBySymbol(String symbol);

    Mono<CoinDTO> getByName(String name);

    Flux<HistoryInfoDTO> getAllHistory(String id, String vs_currency, String days, Optional<String> interval);

    Flux<CandleInfoDTO> getAllCandles(String id, String vs_currency, String days);

}
