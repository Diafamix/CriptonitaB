package com.cryptonita.app.data.providers;

import com.cryptonita.app.core.utils.Validate;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICoinProvider {


    /**
     * This method creates a new coin in the database.
     * @param name name of the coin
     * @return  object created
     */
    CoinResponseDTO createCoin(String coinID, String name, String symbol);

    /**
     * This method returns a list of all coins.
     * @return List of coins
     */
    List<CoinResponseDTO> getAllCoins();

    default List<CoinResponseDTO> getCoins(String ids) {
        return getCoins(ids.split(","));
    }

    List<CoinResponseDTO> getCoins(String... ids);

    /**
     * This method deletes a coin by name
     * @param  name of coin
     * @return the deleted coin
     */
    CoinResponseDTO deleteByName(String name);

    /**
     * This method get a coin by name
     * @param name of coin
     * @return The called coin
     */
    CoinResponseDTO getCoinByName(String name);

    /**
     * This method get a coin by its id
     * @param id of coin to search
     * @return The called coin
     */
    CoinResponseDTO getCoinById(String coinID);

    /**
     * This method get a coin by its rank
     * @param rank of coin to search
     * @return The called coin
     */
    CoinResponseDTO getByRank(int rank);

    /**
     * This method get a coin by its symbol
     * @param symbol of coin to search
     * @return The called coin
     */
    CoinResponseDTO getBySymbol(String symbol);

    /**
     * Method to check if a coin exists
     *
     * @param id the id of the coin to search
     * @return true if exists, false if not
     */
    default boolean exists(String coinID) {
        return Validate.testAndTry(() -> getCoinById(coinID));
    }

    default boolean existsByName(String name) {
        return Validate.testAndTry(() -> getCoinByName(name));
    }

    /**
     * Method to check if a coin exists
     *
     * @param rank the rank of the coin to search
     * @return true if exists, false if not
     */
    default boolean existsByRank(int rank) {
        return Validate.testAndTry(() -> getByRank(rank));
    }

    /**
     * Method to check if a coin exists
     *
     * @param symbol the symbol of the coin to search
     * @return true if exists, false if not
     */
    default boolean existsBySymbol(String symbol) {
        return Validate.testAndTry(() -> getBySymbol(symbol));
    }

}
