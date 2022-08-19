package com.cryptonita.app.data.providers.impl;

import com.cryptonita.app.core.controllers.services.IPortfolioService;
import com.cryptonita.app.data.daos.IHistoryDao;
import com.cryptonita.app.data.daos.IUserDao;
import com.cryptonita.app.data.entities.HistoryModel;
import com.cryptonita.app.data.entities.UserModel;
import com.cryptonita.app.data.providers.IAccountProvider;
import com.cryptonita.app.data.providers.IRegisterProvider;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.HistoryResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.exceptions.data.HistoryNotFoundException;
import com.cryptonita.app.exceptions.data.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class RegisterProviderImp implements IRegisterProvider {

    private static final String USER_ALREADY_EXISTS = "The user already exists!";
    private static final String HISTORY_ALREADY_EXISTS = "The history %s already exists!";

    private final IHistoryDao historyDao;
    private final IUserDao userDao;
    private final IMapper<HistoryModel, HistoryResponseDTO> responseMapper;
    private final IMapper<UserModel, UserResponseDTO> userResponseDTOIMapper;
    private final IAccountProvider accountProvider;
    private final ObjectMapper jsonMapper;

    @SneakyThrows
    @Transactional
    @Override
    public synchronized HistoryResponseDTO log(String username, LocalDate date, String origin, String destiny, double quantity) {

        UserModel userModel = userDao.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(USER_ALREADY_EXISTS));

        HistoryModel model = HistoryModel.builder()
                .user(userModel)
                .date(date)
                .origin(origin)
                .destiny(destiny)
                .quantity(quantity)
                .portfolio(jsonMapper.writeValueAsString(accountProvider.getAllFromUser(userModel.getUsername())))
                .build();

        historyDao.save(model);

        return HistoryResponseDTO.builder()
                .user(userResponseDTOIMapper.mapToDto(userModel))
                .destiny(model.getDestiny())
                .origin(model.getOrigin())
                .date(model.getDate())
                .quantity(model.getQuantity())
                .build();
    }

    @Override
    public synchronized List<HistoryResponseDTO> getLogsFromUsers(String user, LocalDate start, LocalDate end) {

        UserModel userModel = userDao.findByUsername(user)
                .orElseThrow(() -> new UserNotFoundException(USER_ALREADY_EXISTS));

        return historyDao.findAllByUser_UsernameAndDateAfterAndDateBefore(user,start,end).stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized HistoryResponseDTO getOneRegister(long id) {
        HistoryModel historyModel = historyDao.findById(id)
                .orElseThrow(() -> new HistoryNotFoundException(String.format(HISTORY_ALREADY_EXISTS,id)));

        return responseMapper.mapToDto(historyModel);
    }


}
