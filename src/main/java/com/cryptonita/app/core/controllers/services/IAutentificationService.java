package com.cryptonita.app.core.controllers.services;

import com.cryptonita.app.core.services.impl.EmailService;
import com.cryptonita.app.dto.data.request.UserRegisterDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;

public interface IAutentificationService {

    UserResponseDTO register(UserRegisterDTO userRegisterDTO);

    boolean login(String username, String password);

    boolean loginv2(String mail, String password);

    UserResponseDTO retrieve(String email);

}
