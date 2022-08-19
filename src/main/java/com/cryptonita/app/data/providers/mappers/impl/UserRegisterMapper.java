package com.cryptonita.app.data.providers.mappers.impl;

import com.cryptonita.app.data.entities.UserModel;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.request.UserRegisterDTO;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterMapper implements IMapper<UserModel, UserRegisterDTO> {

    @Override
    public UserRegisterDTO mapToDto(UserModel user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserModel mapToEntity(UserRegisterDTO dto) {
        return UserModel.builder()
                .mail(dto.mail)
                .username(dto.username)
                .password(dto.password)
                .role(dto.role)
                .type(dto.type)
                .build();
    }

}
