package com.cryptonita.app.data.providers;

import com.cryptonita.app.dto.data.response.RestarResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;

public interface IRestartProvider {

    RestarResponseDTO findRestart(String month, String year);

    RestarResponseDTO newRestart(RestarResponseDTO restarResponseDTO);
}
