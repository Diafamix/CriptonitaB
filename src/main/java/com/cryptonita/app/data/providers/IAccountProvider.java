package com.cryptonita.app.data.providers;

import com.cryptonita.app.dto.data.response.WalletResponseDto;

import java.util.List;

public interface IAccountProvider {

    /**
     * Convenient method to get a specific account of a user
     *
     * @param user the username of the user involved in the transaction
     * @param coin the coin name to get the account from
     * @return the dto with all the information of the account
     */
    WalletResponseDto get(String user, String coin);

    /**
     * Convenient method of creating a user-specific account
     *
     * @param user the username of the user involved in the transaction
     * @param coin the coin name to get the account from
     * @return the dto with all the information of the account
     */
    WalletResponseDto create(String user, String coin);

    /**
     * Convenient method for entering currencies to a specific user account
     *
     * @param user   the username of the user involved in the transaction
     * @param coin   the coin name to get the account from
     * @param amount the amount of the user's coins
     * @return the dto with all the information of the account
     */
    WalletResponseDto deposit(String user, String coin, double amount);

    /**
     * Convenient method for withdraw money from a certain user and a certain coin
     *
     * @param user   the username of the user involved in the transaction
     * @param coin   the coin name to get the account from
     * @param amount the amount of the user's coins
     * @return the dto with all the information of the account
     */
    WalletResponseDto withDraw(String user, String coin, double amount);

    /**
     * Convenient method for clear
     *
     * @param user the username of the user involved in the transaction
     * @param coin the coin name to get the account from
     * @return the dto with all the information of the account
     */
    WalletResponseDto clear(String user, String coin);

    /**
     * Convenient method to get all account of a user
     *
     * @param user the username of the user involved in the transaction
     * @return the dto with all the information of the account
     */
    List<WalletResponseDto> getAllFromUser(String user);

}
