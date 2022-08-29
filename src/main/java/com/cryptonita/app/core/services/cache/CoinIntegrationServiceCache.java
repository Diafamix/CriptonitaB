package com.cryptonita.app.core.services.cache;

import com.cryptonita.app.dto.integration.*;
import com.cryptonita.app.integration.services.ICoinIntegrationService;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class that decorates the integration main service.
 * Caches same responses for 10 mins so we can somehow
 * bypass the min rate limitations of coingecko's api
 */
@Service
@Qualifier("Cache")
public class CoinIntegrationServiceCache implements ICoinIntegrationService {

    private static final String INFO_PREFIX = "INFO";
    private static final String MARKET_PREFIX = "MARKET";
    private static final String HISTORY_PREFIX = "HISTORY";

    @Autowired
    @Qualifier("Impl")
    private ICoinIntegrationService vanillaService;
    private final Cache<String, Object> cache;

    public CoinIntegrationServiceCache() {
        cache = Caffeine.newBuilder()
                .expireAfter(new Expiry<String, Object>() {
                    @Override
                    public long expireAfterCreate(String s, Object integer, long l) {
                        return TimeUnit.MINUTES.toNanos(10);
                    }

                    @Override
                    public long expireAfterUpdate(String s, Object integer, long l, @NonNegative long l1) {
                        return l1;
                    }

                    @Override
                    public long expireAfterRead(String s, Object integer, long l, @NonNegative long l1) {
                        return l1;
                    }
                })
                .build();
    }

    @Override
    public Mono<CoinMetadataDTO> getInfo(String coinID) {
        String key = String.format("%s_%s", INFO_PREFIX, coinID);
        Object cacheValue = cache.asMap().getOrDefault(key, null);

        return (cacheValue != null)
                ? Mono.just((CoinMetadataDTO) cacheValue)
                : vanillaService.getInfo(coinID)
                .doOnNext(coinMetadataDTO -> cache.put(key, coinMetadataDTO));
    }

    @Override
    public Flux<CoinMetadataDTO> getAllInfos(String... coinID) {
        return vanillaService.getAllInfos(coinID);
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarkets(String vs_currency) {
        String key = String.format("%s_%s", MARKET_PREFIX, "all");
        Object cacheValue = cache.asMap().getOrDefault(key, null);
        List<CoinMarketDTO> cacheArray = new ArrayList<>();

        return (cacheValue != null)
                ? Flux.fromIterable((List<CoinMarketDTO>) cacheValue)
                : vanillaService.getAllMarkets()
                    .doOnNext(cacheArray::add)
                    .doOnComplete(() -> cache.put(key, cacheArray));
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarkets(String vs_currency, String ids, String category, String order, Integer per_page, Integer page, Boolean sparkline, String price_change_percentage) {
        return vanillaService.getAllMarkets(vs_currency, ids, category, order, per_page, page, sparkline, price_change_percentage);
    }

    @Override
    public Flux<CoinMarketDTO> getAllMarkets(String vs_currency, String ids) {
        String key = String.format("%s_%s_%s", MARKET_PREFIX, vs_currency, ids);
        Object cacheValue = cache.asMap().getOrDefault(key, null);
        List<CoinMarketDTO> cacheList = new ArrayList<>();

        return (cacheValue != null)
                ? Flux.fromIterable((List<CoinMarketDTO>) cacheValue)
                : vanillaService.getAllMarketByIds(ids)
                    .doOnNext(coinMarketDTO -> {
                        cacheList.add(coinMarketDTO);
                        cache.put(key, cacheList);
                    });
    }

    @Override
    public Mono<CoinHistoricalMarketDTO> getHistorical(String coinId, LocalDate dateAt) {
        String key = String.format("%s_%s_%s", MARKET_PREFIX, coinId, dateAt.toString());
        Object cacheValue = cache.asMap().getOrDefault(key, null);

        return (cacheValue != null)
                ? Mono.just((CoinHistoricalMarketDTO) cacheValue)
                : vanillaService.getHistorical(coinId, dateAt)
                    .doOnNext(coinHistoricalMarketDTO -> cache.put(key, coinHistoricalMarketDTO));
    }

    @Override
    public Flux<CandleInfoDTO> getCandleOfCoin(String id, String vs_currency, String days) {
        return vanillaService.getCandleOfCoin(id, vs_currency, days);
    }

    @Override
    public Mono<ConversorDTO> convert(String from, double quantity) {
        return vanillaService.convert(from, quantity);
    }

    @Override
    public Mono<ConversorDTO> convert(String from, String to, double quantity) {
        return vanillaService.convert(from, to, quantity);
    }

    @Override
    public Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days, String interval) {
        String key = String.format("%s_%s_%s_%s_%s", HISTORY_PREFIX, id, vs_currency, days, interval);

        Object cacheValue = cache.asMap().getOrDefault(key, null);
        List<HistoryInfoDTO> cacheList = new ArrayList<>();

        return (cacheValue != null)
                ? Flux.fromIterable((List<HistoryInfoDTO>) cacheValue)
                : vanillaService.getHistoryOfCoin(id, vs_currency, days, interval)
                    .doOnNext(cacheList::add)
                    .doOnComplete(() -> cache.put(key, cacheList));
    }

    @Override
    public Flux<HistoryInfoDTO> getHistoryOfCoin(String id, String vs_currency, String days) {
        String key = String.format("%s_%s_%s_%s", HISTORY_PREFIX, id, vs_currency, days);

        Object cacheValue = cache.asMap().getOrDefault(key, null);
        List<HistoryInfoDTO> cacheList = new ArrayList<>();

        return (cacheValue != null)
                ? Flux.fromIterable((List<HistoryInfoDTO>) cacheValue)
                : vanillaService.getHistoryOfCoin(id, vs_currency, days)
                    .doOnNext(cacheList::add)
                    .doOnComplete(() -> cache.put(key, cacheList));
    }
}
