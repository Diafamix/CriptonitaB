package com.cryptonita.app.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Create Stacking JPA Entity, this class is relathionship whit UserModel and CoinModel
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "Stacking_table")
public class StackingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    private CoinModel coin;

    @Column(name="Created_At")
    private LocalDateTime createdAt;

    @Column(name="Days_to_expire")
    private int daysToExpire;

    @Column(name="Quantity")
    private double quantity;


    @ManyToOne
    private UserModel user;

    @Builder
    public StackingModel(CoinModel coin, LocalDateTime createdAt, int daysToExpire, double quantity, UserModel user) {
        this.coin = coin;
        this.createdAt = createdAt;
        this.daysToExpire = daysToExpire;
        this.quantity = quantity;
        this.user = user;
    }
}
