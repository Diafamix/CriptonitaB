package com.cryptonita.app.integration.services.impl;

import com.cryptonita.app.dto.integration.*;
import com.cryptonita.app.integration.adapters.*;
import com.cryptonita.app.integration.services.ICoinIntegrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CoinIntegrationServiceImpl implements ICoinIntegrationService {

    private final ICoinInfoAdapter infoAdapter;
    private final ICoinMarketAdapter coinMarketAdapter;
    private final ICandleCapMetaAdapter candleCapMetaAdapter;
    private final IConvertorAdapter convertorAdapter;
    private final IHistoryAdapter historyAdapter;
    private final IHistoricalMarketAdapter historicalMarketAdapter;

    @Override
    public Mono<CoinMetadataDTO> getInfo(String coinID) {
        return infoAdapter.get(coinID);
    }

    @Override
    public Flux<CoinMetadataDTO> getAllInfos(String... coinIDs) {
        return infoAdapter.get(coinIDs);
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarkets() {
        return coinMarketAdapter.getManyCoins();
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarkets(String vs_currency) {
        return coinMarketAdapter.getManyCoins(vs_currency);
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarkets(String vs_currency, String ids, String category, String order, Integer per_page, Integer page, Boolean sparkline, String price_change_percentage) {
        return coinMarketAdapter.getManyCoinsMetadata(vs_currency, ids, category, order, per_page, page, sparkline, price_change_percentage);
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarkets(String vs_currency, String... ids) {
        return coinMarketAdapter.getManyCoinsByIds(vs_currency, ids);
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarkets(String vs_currency, String ids) {
        return coinMarketAdapter.getManyCoinsByIds(vs_currency, ids);
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarketByIds(String ids) {
        return coinMarketAdapter.getManyCoinsByIds(ids);
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarketByIds(String... ids) {
        return coinMarketAdapter.getManyCoinsByIds(ids);
    }

    @Override
    public Mono<CoinHistoricalMarketDTO> getHistorical(String coinId, LocalDate dateAt) {
        return historicalMarketAdapter.getHistorical(coinId, dateAt);
    }

    @Override
    public Flux<CandleInfoDTO> getCandleOfCoin(String id, String vs_currency, String days) {
        return candleCapMetaAdapter.getCandleOfCoin(id, vs_currency, days);
    }

    @Override
    public Mono<ConversorDTO> convert(String from, double quantity) {
        return convertorAdapter.convert(from, quantity);
    }

    @Override
    public Mono<ConversorDTO> convert(String from, String to, double quantity) {
        return convertorAdapter.convert(from, to, quantity);
    }

    @Override
    public Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days, String interval) {
        return historyAdapter.getHistoryOfCoin(id, vs_currency, days, interval);
    }

    @Override
    public Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days) {
        return historyAdapter.getHistoryOfCoin(id, vs_currency, days);
    }

}
