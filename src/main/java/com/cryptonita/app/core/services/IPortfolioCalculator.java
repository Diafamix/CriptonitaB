package com.cryptonita.app.core.services;

import com.cryptonita.app.dto.integration.HistoryInfoDTO;

import java.time.LocalDate;
import java.util.List;

public interface IPortfolioCalculator {

    List<HistoryInfoDTO> calculateAllTime(String username);

    List<HistoryInfoDTO> calculateInterval(String username, LocalDate start, LocalDate end);

}
