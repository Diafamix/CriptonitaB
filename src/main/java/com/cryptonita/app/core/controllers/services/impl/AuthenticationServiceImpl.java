package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IAutentificationService;
import com.cryptonita.app.data.providers.impl.UserProviderImpl;
import com.cryptonita.app.dto.data.request.UserRegisterDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements IAutentificationService {

    UserProviderImpl userProvider;

    @Override
    public UserResponseDTO register(UserRegisterDTO userRegisterDTO) {

        UserRegisterDTO newUser = UserRegisterDTO.builder()
                .mail(userRegisterDTO.mail)
                .password(userRegisterDTO.password)
                .role(userRegisterDTO.role)
                .type(userRegisterDTO.type)
                .username(userRegisterDTO.username)
                .build();

        return userProvider.register(newUser);
    }

    @Override
    public boolean login(String username, String password) {
        return userProvider.matchesPasswordByUsername(username, password);
    }

    @Override
    public boolean loginv2(String mail, String password) {
        return userProvider.matchesPassword(mail, password);
    }
}
