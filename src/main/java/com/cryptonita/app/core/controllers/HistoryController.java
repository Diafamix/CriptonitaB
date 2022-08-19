package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.IHistoryService;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("/api/history")
@CrossOrigin("*")
@Tag(name = "History")
public class HistoryController {

    private final IHistoryService historyService;

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get historical operations of the current user at a given date range")
    public RestResponse getHistoryByUserName(LocalDate start, LocalDate end){
        return RestResponse.encapsulate(historyService.getAllRegisterUser(start,end));
    }

    @GetMapping("/dowload")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get historical operations of the current user as a pdf at a given date range (BETA)")
    public RestResponse dowloadHistory(LocalDate start,LocalDate end){return null;}
}
