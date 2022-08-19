package com.cryptonita.app.core.controllers.services.impl;

import com.cryptonita.app.core.controllers.services.IConvertorService;
import com.cryptonita.app.core.controllers.services.ISwapService;
import com.cryptonita.app.data.entities.enums.UserType;
import com.cryptonita.app.data.providers.IAccountProvider;
import com.cryptonita.app.data.providers.IRegisterProvider;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.response.SwapResponseDto;
import com.cryptonita.app.dto.data.response.SwapUsersResponseDto;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.dto.data.response.WalletResponseDto;
import com.cryptonita.app.dto.integration.ConversorDTO;
import com.cryptonita.app.security.SecurityContextHelper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class SwapServiceImpl implements ISwapService {

    private final IUserProvider userProvider;
    private final IAccountProvider accountProvider;
    private final IConvertorService convertorService;
    private final SecurityContextHelper securityContextHelper;

    private final IRegisterProvider registerProvider;


    @Transactional
    @Override
    public SwapResponseDto swap(String from, String to, double amount) {
        UserResponseDTO user = securityContextHelper.getUser();
        ConversorDTO conversorDTO = convertorService.convert(from, to, amount).block();

        double toDeposit = conversorDTO.price * user.getType().getComission();

        accountProvider.withDraw(user.username, from, amount);
        accountProvider.deposit(user.username, to, toDeposit);

        registerProvider.log(user.username, LocalDate.now(), from, to, amount);

        return SwapResponseDto.builder()
                .userName(user.username)
                .walletFrom(from)
                .walletTo(to)
                .amuountFrom(amount)
                .amountTo(toDeposit)
                .build();
    }

    @Override
    public SwapUsersResponseDto swap(String userTarget, String from, String to, double amount) {
        UserResponseDTO userFrom = securityContextHelper.getUser();
        ConversorDTO conversorDTO = convertorService.convert(from, to, amount).block();
        UserResponseDTO userTo = userProvider.getByName(userTarget);

        double toDeposit = conversorDTO.price * userTo.getType().getComission();

        accountProvider.withDraw(userFrom.username, from, amount);
        accountProvider.deposit(userTo.username, to, toDeposit);

        registerProvider.log(userFrom.username, LocalDate.now(), from, to, amount);
        registerProvider.log(userTo.username, LocalDate.now(), to, from, toDeposit);

        return SwapUsersResponseDto.builder()
                .userFrom(userFrom.getUsername())
                .userTo(userTo.getUsername())
                .walletFrom(from)
                .walletTo(to)
                .amountFrom(amount)
                .amountTo(toDeposit)
                .build();
    }

}
