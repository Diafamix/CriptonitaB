package com.cryptonita.app.data.providers.impl;

import com.cryptonita.app.data.daos.IRestartDAO;
import com.cryptonita.app.data.entities.RestartModel;
import com.cryptonita.app.data.providers.IRestartProvider;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.RestarResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class RestartProviderImpl implements IRestartProvider {

    private final IRestartDAO restartDAO;

    private final IMapper<RestartModel, RestarResponseDTO> mapper;

    @Override
    public RestarResponseDTO findRestart(String month, String year) {
        return restartDAO.findByMonthAndAndYear(month, year)
                .map(mapper::mapToDto)
                .orElse(null);
    }

    @Override
    public RestarResponseDTO newRestart(RestarResponseDTO restarResponseDTO) {
        RestartModel model = mapper.mapToEntity(restarResponseDTO);

        return mapper.mapToDto(restartDAO.save(model));
    }
}
