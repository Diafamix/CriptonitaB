package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.ISwapService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.core.controllers.utils.TokenConsume;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/swap")
@AllArgsConstructor
@CrossOrigin("*")
@Tag(name = "Swap")
public class SwapController {

    private final ISwapService swapService;

    @GetMapping("/trade")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Trades a certain amount of a coin to another in the portfolio of the current user")
    @TokenConsume(1)
    public RestResponse trade(String from, String to, Double amount) {
        return RestResponse.encapsulate(swapService.swap(from, to, amount));
    }

    @GetMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Send a certain amount of a coin from the current user to the given user")
    @TokenConsume(1)
    public RestResponse send(String userTo, String from, String to, Double amount) {
        return RestResponse.encapsulate(swapService.swap(userTo,from,to,amount));
    }



}
