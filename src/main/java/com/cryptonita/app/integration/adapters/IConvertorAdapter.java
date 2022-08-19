package com.cryptonita.app.integration.adapters;

import com.cryptonita.app.data.entities.CoinModel;
import com.cryptonita.app.dto.integration.ConversorDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import reactor.core.publisher.Mono;

public interface IConvertorAdapter {

    /**
     * Convenient method to convert money from one coin to USD
     *
     * @param from the currency symbol of the coin to convert from
     * @param quantity An amount of currency to convert
     * @return the dto with all the information of the conversion
     */
    default Mono<ConversorDTO> convert(String from, double quantity) {
        return convert(from, "USD", quantity);
    }

    /**
     * Convenient method to convert money from one coin to another
     *
     * @param from the currency symbol of the coin to convert from
     * @param to symbols to convert the source amount to
     * @param quantity An amount of currency to convert
     * @return the dto with all the information of the conversion
     */
    Mono<ConversorDTO> convert(String from, String to, double quantity);

}
