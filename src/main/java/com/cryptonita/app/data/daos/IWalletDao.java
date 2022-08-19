package com.cryptonita.app.data.daos;

import com.cryptonita.app.data.entities.WalletModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Wallet repository
 * */
public interface IWalletDao extends JpaRepository <WalletModel , Long> {

    Optional<WalletModel> findById(long id);

    Optional<WalletModel>findByUser_UsernameAndCoinName(String userName, String coin);

    List<WalletModel> findAllByUserUsername(String name);

    Optional<WalletModel> deleteById(long id);



}
