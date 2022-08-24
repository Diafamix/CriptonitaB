package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.IPortfolioService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.core.controllers.utils.TokenConsume;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@AllArgsConstructor
@RequestMapping("/api/portfolio")
@CrossOrigin("*")
@Tag(name = "Portfolio")
@Validated
public class PortfolioController {

    private final IPortfolioService portfolioService;

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieves information of a wallet identified with a coin name from the current user")
    @TokenConsume(1)
    public RestResponse get(@NotBlank String coin) {
        return RestResponse.encapsulate(portfolioService.get(coin));
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets the portfolio (totalBalance, coins, coin market data and allocation) for the current user ")
    @TokenConsume(1)
    public RestResponse getPortfolio() {
        return RestResponse.encapsulate(portfolioService.getAll());
    }

    @GetMapping("/chart")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets the portfolio chart of the user ")
    @TokenConsume(4)
    public RestResponse getPortfolioChart() {
        return RestResponse.encapsulate(portfolioService.getPortfolioChart());
    }

}
