package com.cryptonita.app.data.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Create Wallet JPA Entity
 **/

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WALLET")
public class WalletModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    private UserModel user;

    @ManyToOne
    @JoinColumn
    private CoinModel coin;

    private double quantity;

    @Builder
    public WalletModel(UserModel user, CoinModel coin, double quantity) {
        this.user = user;
        this.coin = coin;
        this.quantity = quantity;
    }
}
