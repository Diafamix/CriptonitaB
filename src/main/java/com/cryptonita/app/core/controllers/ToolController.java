package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.IConvertorService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.core.controllers.utils.TokenConsume;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/api/tools")
@AllArgsConstructor
@CrossOrigin("*")
@Tag(name = "Tools")
public class ToolController {

    private final IConvertorService convertorService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Convenient method to convert a coin to another with the most recent data")
    @GetMapping("/convert")
    @TokenConsume(1)
    public Mono<RestResponse> convert(String from, @RequestParam(required = false) Optional<String> to, double amount) {
        return to.map(s -> convertorService.convert(from, s, amount)
                        .map(RestResponse::encapsulate)
                )
                .orElseGet(() -> convertorService.convert(from, amount)
                        .map(RestResponse::encapsulate)
                );

    }

}
