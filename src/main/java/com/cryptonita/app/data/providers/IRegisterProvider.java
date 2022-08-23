package com.cryptonita.app.data.providers;

import com.cryptonita.app.dto.data.request.RegisterRequestDTO;
import com.cryptonita.app.dto.data.response.HistoryResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface IRegisterProvider {

    default HistoryResponseDTO log(RegisterRequestDTO dto) {
        return log(dto.user, dto.date, dto.origin, dto.destiny, dto.quantity);
    }

    HistoryResponseDTO log(String username, LocalDate date, String origin, String destiny, double quantity);

    List<HistoryResponseDTO> getLogsFromUsers(String user, LocalDate start, LocalDate end);

    HistoryResponseDTO getOneRegister(long id);

}
