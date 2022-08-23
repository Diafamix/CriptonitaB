package com.cryptonita.app.dto.data.request;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class RegisterRequestDTO {

    public final String user;
    public final LocalDate date;
    public final String origin;
    public final String destiny;
    public final double quantity;

}
