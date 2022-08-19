package com.cryptonita.app.integration.adapters;

import com.cryptonita.app.dto.integration.CandleInfoDTO;
import reactor.core.publisher.Flux;

public interface ICandleCapMetaAdapter {

    /**
     *
     * Retrieves a candle of the desired crypto
     *
     * @param id exchange id
     * @param vs_currency price in foreign currency
     * @param days time in milliseconds.
     *
     * @return a reactive flux with the dtos carrying the info
     */

    Flux<CandleInfoDTO> getCandleOfCoin(String id, String vs_currency, String days);

}
