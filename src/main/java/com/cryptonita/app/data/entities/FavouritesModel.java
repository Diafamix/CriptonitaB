package com.cryptonita.app.data.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = "user")
@Table(name = "Favourites")
public class FavouritesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn
    private UserModel user;

    @ManyToOne
    @JoinColumn
    private CoinModel coin;


}



