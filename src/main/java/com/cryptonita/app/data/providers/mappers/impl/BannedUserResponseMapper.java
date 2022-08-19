package com.cryptonita.app.data.providers.mappers.impl;

import com.cryptonita.app.data.entities.BannedUsersModel;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.BannedUserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BannedUserResponseMapper implements IMapper<BannedUsersModel, BannedUserResponseDTO> {

    @Override
    public BannedUserResponseDTO mapToDto(BannedUsersModel bannedUser) {
        return BannedUserResponseDTO.builder()
                .userID(bannedUser.getUser().getId())
                .username(bannedUser.getUser().getUsername())
                .bannedAt(bannedUser.getBannedAt())
                .expiresAt(bannedUser.getExpiresAt())
                .build();
    }

    @Override
    public BannedUsersModel mapToEntity(BannedUserResponseDTO bannedUserResponseDTO) {
        throw new UnsupportedOperationException();
    }
}
