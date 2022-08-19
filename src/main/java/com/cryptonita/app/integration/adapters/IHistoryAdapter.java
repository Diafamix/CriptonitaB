package com.cryptonita.app.integration.adapters;

import com.cryptonita.app.dto.integration.HistoryInfoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IHistoryAdapter {

    /**
     *
     * Retrieves a history of the desired crypto
     *
     * @param symbol the symbol of the coin to search
     * @param interval point-in-time interval.
     *
     * @return a reactive flux with the dtos carrying the info
     */
    Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days, String interval);

    /**
     *
     * Retrieves a history of the desired crypto
     *
     * @param symbol the symbol of the coin to search
     *
     * @return a reactive flux with the dtos carrying the info
     */
    Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days);



}
