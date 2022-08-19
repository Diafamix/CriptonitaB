package com.cryptonita.app.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ConversorDTO {

    public String from; // The coin to be converted
    public String to;  // The coin to be converted to
    public double amount; // The amount to convert
    public double price; // The price of the target coin
    public LocalDate time;

}
