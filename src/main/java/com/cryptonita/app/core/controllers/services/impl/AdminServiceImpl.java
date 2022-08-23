package com.cryptonita.app.core.controllers.services.impl;


import com.cryptonita.app.core.controllers.services.IAdminService;
import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.data.entities.enums.UserType;
import com.cryptonita.app.data.providers.ICoinProvider;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.request.UserRegisterDTO;
import com.cryptonita.app.dto.data.response.BannedUserResponseDTO;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.security.SecurityContextHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AdminServiceImpl implements IAdminService {

    private final ICoinProvider coinProvider;
    private final IUserProvider userProvider;
    private final SecurityContextHelper securityContextHelper;


    @Override
    public CoinResponseDTO createCoin(String coinID, String name, String symbol) {
        //log.info(); //TODO
        return coinProvider.createCoin(coinID, name, symbol);
    }

    @Override
    public CoinResponseDTO deleteCoin(String name) {
        return coinProvider.deleteByName(name);
    }

    @Override
    public BannedUserResponseDTO banUser(String mail) {
        return userProvider.banUser(mail);
    }

    @Override
    public BannedUserResponseDTO unBanUser(String mail) {
        return userProvider.unBanUser(mail);
    }

    @Override
    public UserResponseDTO getUserById(long id) {
        return userProvider.getById(id);
    }

    @Override
    public UserResponseDTO createUser(String mail, String username, UserRole userRole, UserType userType) {
        return userProvider.register(UserRegisterDTO.builder()
                .mail(mail)
                .username(username)
                .role(userRole)
                .password("12345")
                .type(userType)
                .build());
    }

    @Override
    public UserResponseDTO changeUserType(String mail, UserType userType) {
        return userProvider.changeUserType(mail, userType);
    }


}
