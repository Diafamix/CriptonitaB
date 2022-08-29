package com.cryptonita.app.integration.services;

import com.cryptonita.app.dto.integration.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface ICoinIntegrationService {

    Mono<CoinMetadataDTO> getInfo(String coinID);

    Flux<CoinMetadataDTO> getAllInfos(String... coinID);

    default Flux<CoinMarketDTO> getAllMarkets() {
        return getAllMarkets("usd");
    }

    Flux<CoinMarketDTO> getAllMarkets(String vs_currency);

    Flux<CoinMarketDTO> getAllMarkets(String vs_currency, String ids, String category, String order, Integer per_page,
                                      Integer page, Boolean sparkline, String price_change_percentage);

    default Flux<CoinMarketDTO> getAllMarkets(String vs_currency, String... ids) {
        return getAllMarkets(vs_currency, String.join(",", ids));
    }

    Flux<CoinMarketDTO> getAllMarkets(String vs_currency, String ids);

    default Flux<CoinMarketDTO> getAllMarketByIds(String ids) {
        return getAllMarkets("usd", ids);
    }

    default Flux<CoinMarketDTO> getAllMarketByIds(String... ids) {
        return getAllMarketByIds(String.join(",", ids));
    }

    Mono<CoinHistoricalMarketDTO> getHistorical(String coinId, LocalDate dateAt);

    Flux<CandleInfoDTO> getCandleOfCoin(String id, String vs_currency, String days);

    /**
     * Convenient method to convert money from one coin to USD
     *
     * @param from     the currency symbol of the coin to convert from
     * @param quantity An amount of currency to convert
     * @return the dto with all the information of the conversion
     */
    Mono<ConversorDTO> convert(String from, double quantity);

    /**
     * Convenient method to convert money from one coin to another
     *
     * @param from     the currency symbol of the coin to convert from
     * @param to       symbols to convert the source amount to
     * @param quantity An amount of currency to convert
     * @return the dto with all the information of the conversion
     */
    Mono<ConversorDTO> convert(String from, String to, double quantity);

    /**
     * Retrieves a history of the desired crypto
     *
     * @param symbol   the symbol of the coin to search
     * @param interval point-in-time interval.
     * @return a reactive flux with the dtos carrying the info
     */
    Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days, String interval);

    /**
     * Retrieves a history of the desired crypto
     *
     * @param symbol the symbol of the coin to search
     * @return a reactive flux with the dtos carrying the info
     */
    Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days);

}
