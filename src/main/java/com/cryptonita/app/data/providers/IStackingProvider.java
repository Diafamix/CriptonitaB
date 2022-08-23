package com.cryptonita.app.data.providers;

import com.cryptonita.app.dto.data.response.StackingDTO;

import java.util.List;

public interface IStackingProvider {

    StackingDTO stake(String userName, String coinName, double quantity, int daysToExpire);

    StackingDTO unStake(long id, String userName);

    List<StackingDTO> getAllUserStakes(String username);

    StackingDTO getUserStake(long id, String username);

    List<StackingDTO> findAll();

}
