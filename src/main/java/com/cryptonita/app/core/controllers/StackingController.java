package com.cryptonita.app.core.controllers;


import com.cryptonita.app.core.controllers.services.IStackingService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.core.controllers.utils.TokenConsume;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/stacking")
@CrossOrigin("*")
@Tag(name = "Stacking")
public class StackingController {

    private final IStackingService stackingService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets all the current active stacks for all the users. Needs ADMIN privileges")
    @TokenConsume(1)
    public RestResponse getAll() {
        return RestResponse.encapsulate(stackingService.findAll());
    }

    @GetMapping("/allUser")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets all the current active stacks for the current user")
    @TokenConsume(1)
    public RestResponse getAllUser() {
        return RestResponse.encapsulate(stackingService.findAllByUser());
    }

    @PostMapping("/stake")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Stakes a certain quantity of a coin for certain days for the current user")
    @TokenConsume(1)
    public RestResponse stake(String coinName, double quantity, int daysToExpire) {
        return RestResponse.encapsulate(stackingService.stake(coinName, quantity, daysToExpire));
    }

    @DeleteMapping("/unStake")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Unstackes an stack with the given id")
    @TokenConsume(1)
    public RestResponse unStake(long id) {
        return RestResponse.encapsulate(stackingService.unStake(id));
    }

}
