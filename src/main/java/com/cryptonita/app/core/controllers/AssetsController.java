package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.IAssetsService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/")
@CrossOrigin("*")
@Tag(name = "Assets")
public class AssetsController {

    private final IAssetsService assetsService;

    @GetMapping("assets/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all coins supported")
    public RestResponse list() {
        return RestResponse.encapsulate(assetsService.list());
    }

    @GetMapping("assets/{coinID}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all coins supported")
    public Mono<RestResponse> get(@PathVariable String coinID) {
        return assetsService.getMetadata(coinID)
                .map(RestResponse::encapsulate);
    }

    @GetMapping("markets")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get current data (name, price, market, ...) for all the coins supported")
    public Mono<RestResponse> getAll(@RequestParam(required = false) Optional<String> ids) {
        return ids
                .map(assetsService::getAll)
                .orElseGet(assetsService::getAll)
                .collectList()
                .map(RestResponse::encapsulate);
    }

    @GetMapping("markets/getById/{coinID}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get current data (name, price, market, ... including exchange tickers) for a coin by id")
    public Mono<RestResponse> getById(@PathVariable String coinID) {
        return assetsService.getById(coinID)
                .map(RestResponse::encapsulate);
    }

    @GetMapping("assets/getHistory")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get historical data (name, price, market, stats) at a given date for a coin")
    public Mono<RestResponse> getHistory(String id, String vs_currency,
                                         String days,
                                         @RequestParam Optional<String> interval) {

        return assetsService.getAllHistory(id, vs_currency, days, interval)
                .collectList()
                .map(RestResponse::encapsulate);
    }

    @GetMapping("assets/getCandle")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get historical ohlc at a given date range for a coin")
    public Mono<RestResponse> getCandle(String id, String vs_currency, String days) {
        return assetsService.getAllCandles(id, vs_currency, days)
                .collectList()
                .map(RestResponse::encapsulate);
    }


}
