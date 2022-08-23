package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IAutentificationService;
import com.cryptonita.app.core.services.impl.EmailService;
import com.cryptonita.app.data.providers.impl.UserProviderImpl;
import com.cryptonita.app.dto.data.request.UserRegisterDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.h2.engine.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements IAutentificationService {

    UserProviderImpl userProvider;
    EmailService emailService;

    @Override
    public UserResponseDTO register(UserRegisterDTO userRegisterDTO) {

        UserRegisterDTO newUser = UserRegisterDTO.builder()
                .mail(userRegisterDTO.mail)
                .password(userRegisterDTO.password)
                .role(userRegisterDTO.role)
                .type(userRegisterDTO.type)
                .username(userRegisterDTO.username)
                .build();


        UserResponseDTO responseDTO = userProvider.register(newUser);

        CompletableFuture.runAsync(() ->
                emailService.send(userRegisterDTO.mail, "Registro", "Se registró correctamente"));

        return responseDTO;
    }

    @Override
    public boolean login(String username, String password) {
        return userProvider.matchesPasswordByUsername(username, password);
    }

    @Override
    public boolean loginv2(String mail, String password) {
        return userProvider.matchesPassword(mail, password);
    }

    @Override
    public UserResponseDTO retrieve(String email) {
        return userProvider.changeUserPassword(email);
    }
}
