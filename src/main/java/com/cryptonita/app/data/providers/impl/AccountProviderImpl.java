package com.cryptonita.app.data.providers.impl;

import com.cryptonita.app.data.daos.ICoinDAO;
import com.cryptonita.app.data.daos.IUserDao;
import com.cryptonita.app.data.daos.IWalletDao;
import com.cryptonita.app.data.entities.CoinModel;
import com.cryptonita.app.data.entities.UserModel;
import com.cryptonita.app.data.entities.WalletModel;
import com.cryptonita.app.data.providers.IAccountProvider;
import com.cryptonita.app.data.providers.mappers.IMapper;
import com.cryptonita.app.dto.data.response.WalletResponseDto;
import com.cryptonita.app.exceptions.data.CoinNotFoundException;
import com.cryptonita.app.exceptions.data.UserNotFoundException;
import com.cryptonita.app.exceptions.data.WalletNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AccountProviderImpl implements IAccountProvider {

    private static final String NO_COIN_FOUND = "The coin %s is not supported";
    private static final String COIN_ALREADY_EXISTS = "The coin %s already exists!";
    private static final String USER_ALREADY_EXISTS = "The user %s already exists!";
    private static final String WALLET_ALREADY_EXISTS = "The wallet already exists!";

    private final IWalletDao walletDao;
    private final IUserDao userDao;

    private final ICoinDAO coinDAO;

    private final IMapper<WalletModel, WalletResponseDto> walletMapper;

    @Override
    public synchronized WalletResponseDto get(String user, String coin) {
        return walletDao.findByUser_UsernameAndCoinName(user, coin)
                .map(walletMapper::mapToDto)
                .orElseThrow(() -> new WalletNotFoundException("The Wallet dont exist"));
    }

    @Override
    public synchronized WalletResponseDto create(String user, String coin) {
        UserModel userModel = userDao.findByUsername(user)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_ALREADY_EXISTS, user)));

        CoinModel coinModel = coinDAO.findByName(coin).
                orElseThrow(() -> new CoinNotFoundException(String.format(COIN_ALREADY_EXISTS, coin)));

        WalletModel walletModel = WalletModel.builder()
                .user(userModel)
                .coin(coinModel)
                .build();


        return walletMapper.mapToDto(walletDao.save(walletModel));
    }

    @Override
    public synchronized WalletResponseDto deposit(String user, String coin, double amount) {
        UserModel userModel = userDao.findByUsername(user)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_ALREADY_EXISTS, user)));

        CoinModel coinModel = coinDAO.findByName(coin)
                .orElseThrow(() -> new CoinNotFoundException(String.format(COIN_ALREADY_EXISTS, coin)));

        WalletModel walletModel = userModel.getWallets().stream()
                .filter(walletModel1 -> walletModel1.getCoin().equals(coinModel))
                .findFirst()
                .orElse(null);

        if (walletModel == null) {
            walletModel = WalletModel.builder()
                    .user(userModel)
                    .coin(coinModel)
                    .quantity(0)
                    .build();
        }

        walletModel.setQuantity(walletModel.getQuantity() + amount);

        return walletMapper.mapToDto(walletDao.save(walletModel));
    }

    @Override
    public synchronized WalletResponseDto withDraw(String user, String coin, double amount) {
        UserModel userModel = userDao.findByUsername(user)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_ALREADY_EXISTS, user)));

        CoinModel coinModel = coinDAO.findByCoinID(coin).
                orElseThrow(() -> new CoinNotFoundException(String.format(COIN_ALREADY_EXISTS, coin)));

        WalletModel walletModel = userModel.getWallets().stream()
                .filter(walletModel1 -> walletModel1.getCoin().equals(coinModel))
                .findFirst()
                .orElse(null);

        if (walletModel == null)
            throw new WalletNotFoundException("This Wallet doesnt exist");

        if (walletModel.getQuantity() < amount)
            throw new WalletNotFoundException("This wallet doesnt have sufficient funds ");

        walletModel.setQuantity(walletModel.getQuantity() - amount);

        return walletMapper.mapToDto(walletDao.save(walletModel));
    }


    @Override
    public synchronized WalletResponseDto clear(String user, String coin) {
        UserModel userModel = userDao.findByUsername(user)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_ALREADY_EXISTS, user)));

        CoinModel coinModel = coinDAO.findByCoinID(coin)
                .orElseThrow(() -> new CoinNotFoundException(String.format(COIN_ALREADY_EXISTS, coin)));

        WalletModel wallet = userModel.getWallets().stream()
                .filter(walletModel1 -> walletModel1.getCoin().equals(coinModel))
                .findFirst()
                .orElse(null);

        if (wallet == null)
            throw new WalletNotFoundException(WALLET_ALREADY_EXISTS);

        wallet.setQuantity(0);

        return walletMapper.mapToDto(walletDao.save(wallet));
    }

    @Override
    public synchronized List<WalletResponseDto> getAllFromUser(String user) {
        return walletDao.findAllByUserUsername(user).stream()
                .map(walletMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
