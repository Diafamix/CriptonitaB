package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IExcelService;
import com.cryptonita.app.core.controllers.services.IHistoryService;
import com.cryptonita.app.core.utils.ExcelGenerator;
import com.cryptonita.app.data.providers.IRegisterProvider;
import com.cryptonita.app.dto.data.response.HistoryResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.security.SecurityContextHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ExcelServiceImpl implements IExcelService {

    private final IRegisterProvider registerProvider;
    private final SecurityContextHelper securityContextHelper;

    public void downloadHistory(String start, String end, HttpServletResponse response) throws IOException {

        UserResponseDTO user = securityContextHelper.getUser();

        LocalDate localDateStart = LocalDate.parse(start);
        LocalDate localDateEnd = LocalDate.parse(end);

        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=history" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<HistoryResponseDTO> historyResponseDTOList = registerProvider.getLogsFromUsers(user.username,localDateStart, localDateEnd);

        ExcelGenerator generator = new ExcelGenerator(historyResponseDTOList);

        generator.generateExcelFile(response);

    }
}


