package com.cryptonita.app.core.controllers.services;

import com.cryptonita.app.dto.data.response.SwapResponseDto;
import com.cryptonita.app.dto.data.response.SwapUsersResponseDto;

public interface ISwapService {

    /**
     * Swaps a given amount of a coin from one user wallet to
     * another wallet from the same user
     *
     * @param from   the coin to swap from
     * @param to     the coin to swap to
     * @param amount the amount to swap
     */
    SwapResponseDto swap(String from, String to, double amount);

    /**
     * Swaps a given amount of a coin from one user wallet to another user wallet
     *
     * @param userTarget the user target
     * @param from       the coin to swap from
     * @param to         the coin to swap to
     * @param amount     the amount to swap
     */
    SwapUsersResponseDto swap(String userTarget, String from, String to, double amount);

}
