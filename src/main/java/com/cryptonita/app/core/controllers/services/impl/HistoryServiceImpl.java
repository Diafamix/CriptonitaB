package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IHistoryService;
import com.cryptonita.app.data.providers.IRegisterProvider;
import com.cryptonita.app.dto.data.response.HistoryResponseDTO;
import com.cryptonita.app.security.SecurityContextHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements IHistoryService {

    private final IRegisterProvider registerProvider;
    private final SecurityContextHelper securityContextHelper;

    @Override
    public List<HistoryResponseDTO> getAllRegisterUser(LocalDate start, LocalDate end) {
        return registerProvider.getLogsFromUsers(securityContextHelper.getUser().getUsername(), start, end);
    }

    @Override
    public HistoryResponseDTO getOneRegister(long id) {
        return registerProvider.getOneRegister(id);
    }
}
