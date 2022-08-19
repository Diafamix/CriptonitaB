package com.cryptonita.app.core.controllers.services;

import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.data.entities.enums.UserType;
import com.cryptonita.app.dto.data.response.BannedUserResponseDTO;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import org.h2.engine.User;

public interface IAdminService {

    CoinResponseDTO createCoin(String coinID, String name, String symbol);

    CoinResponseDTO deleteCoin(String name);

    BannedUserResponseDTO banUser(String mail);

    BannedUserResponseDTO unBanUser(String mail);

    UserResponseDTO getUserById(long id);

    UserResponseDTO createUser(String mail, String username, UserRole userRole, UserType userType);

    UserResponseDTO changeUserType(String mail, UserType userType);

}
