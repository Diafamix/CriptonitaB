package com.cryptonita.app.dto.data.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Builder
@Data
public class HistoryResponseDTO {
    public final long id;
    public final UserResponseDTO user;
    public final LocalDate date;
    public final String origin;
    public final String destiny;
    public final double quantity;
    public final String portfolio;
}
