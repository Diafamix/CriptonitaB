package com.cryptonita.app.core.services.impl;

import com.cryptonita.app.core.services.IPortfolioCalculator;
import com.cryptonita.app.data.providers.IRegisterProvider;
import com.cryptonita.app.dto.data.response.HistoryResponseDTO;
import com.cryptonita.app.dto.data.response.WalletResponseDto;
import com.cryptonita.app.dto.integration.CoinHistoricalMarketDTO;
import com.cryptonita.app.dto.integration.HistoryInfoDTO;
import com.cryptonita.app.integration.services.ICoinIntegrationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PortfolioCalculatorImpl implements IPortfolioCalculator {

    private static final double MILLIS_IN_A_DAY = 24 * 60 * 60 * 1000;
    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final TypeReference<List<WalletResponseDto>> PORTFOLIO_TYPE =
            new TypeReference<List<WalletResponseDto>>() {
            };

    private final IRegisterProvider registerProvider;
    private final ICoinIntegrationService coinIntegrationService;

    @Override
    public List<HistoryInfoDTO> calculateAllTime(String username) {

        List<HistoryResponseDTO> histories = registerProvider.getAllLogsFromUser(username).stream()
                .sorted(Comparator.comparing(HistoryResponseDTO::getDate))
                .collect(Collectors.toList());      // Sorts by date

        if (histories.isEmpty()) return new ArrayList<>();

        LocalDate firstDate = histories.get(0).date;
        List<HistoryInfoDTO> chartList = getGranularityList(firstDate, LocalDate.now());

        int currentIndex = 0;
        HistoryEntry current = HistoryEntry.of(histories.get(currentIndex));

        for (ListIterator<HistoryInfoDTO> iterator = chartList.listIterator(); iterator.hasNext(); ) {

            HistoryInfoDTO historyInfoDTO = iterator.next();
            while ((currentIndex + 1) < histories.size()
                    && historyInfoDTO.time >= histories.get(currentIndex + 1).date.atStartOfDay(UTC).toEpochSecond() * 1000) {
                current = HistoryEntry.of(histories.get(++currentIndex));
            }

            double totalPrice = 0.0;
            for (WalletResponseDto wallet : current.wallets) {
                CoinHistoricalMarketDTO historicalDTO = coinIntegrationService
                        .getHistorical(wallet.getCoinName(), parseEpochTimeToLocalDate(historyInfoDTO.time))
                        .block();

                totalPrice += (historicalDTO.price * wallet.getQuantity());
            }

            iterator.set(new HistoryInfoDTO(totalPrice, historyInfoDTO.time));
        }

        return chartList;
    }

    @Override
    public List<HistoryInfoDTO> calculateInterval(String username, LocalDate start, LocalDate end) {
        throw new UnsupportedOperationException(); //TODO
    }

    private List<HistoryInfoDTO> getGranularityList(LocalDate start, LocalDate end) {

        long epochStart = start.atStartOfDay(UTC).toEpochSecond() * 1000;
        long epochEnd = end.atStartOfDay(UTC).toEpochSecond() * 1000;

        long diffOnMillis = (epochEnd - epochStart);
        int jump = (int) Math.ceil(diffOnMillis / MILLIS_IN_A_DAY) + 1;

        long currentTime = epochStart;
        List<HistoryInfoDTO> toReturn = new ArrayList<>(jump);
        for (int i = 0; i < jump; i++) {
            toReturn.add(new HistoryInfoDTO(0.0, currentTime));
            currentTime += MILLIS_IN_A_DAY;
        }

        return toReturn;
    }

    private LocalDate parseEpochTimeToLocalDate(long epochTime) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochTime), ZoneId.systemDefault()).toLocalDate();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    private static final class HistoryEntry {

        private static final ObjectMapper jsonMapper = new ObjectMapper();

        public static HistoryEntry of(HistoryResponseDTO dto) {
            return new HistoryEntry(dto.date, parsePortfolioStr(dto.portfolio));
        }

        @SneakyThrows
        private static List<WalletResponseDto> parsePortfolioStr(String str) {
            return jsonMapper.readerFor(PORTFOLIO_TYPE).readValue(str);
        }

        public final LocalDate date;
        public final List<WalletResponseDto> wallets;

    }

}
