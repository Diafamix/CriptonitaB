package com.cryptonita.app.data.providers.mappers.impl;

import com.cryptonita.app.data.entities.CoinModel;
import com.cryptonita.app.data.entities.StackingModel;
import com.cryptonita.app.data.entities.UserModel;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import com.cryptonita.app.dto.data.response.StackingDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StackingResponseMapper implements IMapper<StackingModel, StackingDTO> {

    private final IMapper<UserModel, UserResponseDTO> userMapper;
    private final IMapper<CoinModel, CoinResponseDTO> coinMapper;

    @Override
    public StackingDTO mapToDto(StackingModel stackingModel) {
        return StackingDTO.builder()
                .user(userMapper.mapToDto(stackingModel.getUser()))
                .coin(coinMapper.mapToDto(stackingModel.getCoin()))
                .quantity(stackingModel.getQuantity())
                .createdAt(stackingModel.getCreatedAt())
                .daysToExpire(stackingModel.getDaysToExpire())
                .build();
    }

    @Override
    public StackingModel mapToEntity(StackingDTO stackingDTO) {
        throw new UnsupportedOperationException();
    }
}
