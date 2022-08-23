package com.cryptonita.app.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "COINS")
public class CoinModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String coinID;

    private String name;

    @Column(unique = true)
    private String symbol;

    @Builder
    public CoinModel(String coinID, String name, String symbol) {
        this.coinID = coinID;
        this.name = name;
        this.symbol = symbol;
    }


}
