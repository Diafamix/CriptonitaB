package com.cryptonita.app.core.controllers;

import com.cryptonita.app.core.controllers.services.IExcelService;
import com.cryptonita.app.core.controllers.services.IHistoryService;
import com.cryptonita.app.core.utils.ExcelGenerator;
import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.core.controllers.utils.TokenConsume;
import com.cryptonita.app.dto.data.response.HistoryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/history")
@CrossOrigin("*")
@Tag(name = "History")
public class HistoryController {

    private final IHistoryService historyService;
    private final IExcelService excelService;

    @GetMapping("/history")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get historical operations of the current user at a given date range")
    @TokenConsume(1)
    public RestResponse getHistoryByUserName(LocalDate start, LocalDate end){
        return RestResponse.encapsulate(historyService.getAllRegisterUser(start,end));
    }

    @GetMapping("/download-to-excel")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get historical operations of the current user as a excel at a given date range (BETA)")
    @TokenConsume(2)
    public void downloadHistory(String start, String end, HttpServletResponse response) throws IOException {
        excelService.downloadHistory(start, end, response);
    }
}
