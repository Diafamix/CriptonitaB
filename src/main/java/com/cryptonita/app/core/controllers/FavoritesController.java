package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.IFavoritesService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.core.controllers.utils.TokenConsume;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/favorites")
@CrossOrigin("*")
@Tag(name = "Favourites")
public class FavoritesController {
    
    private final IFavoritesService favoritesService;

    @GetMapping("/byName")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Gets all the favourites coins of the current user")
    @TokenConsume(1)
    public RestResponse getAll() {
        return RestResponse.encapsulate(favoritesService.getByName());
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Adds a favourite coin to the current user")
    @TokenConsume(1)
    public RestResponse add(String coin) {
        return RestResponse.encapsulate(favoritesService.create(coin));
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Removes a favourite coin from the current user")
    @TokenConsume(1)
    public RestResponse remove(String coin) {
        return RestResponse.encapsulate(favoritesService.delete(coin));
    }

}
