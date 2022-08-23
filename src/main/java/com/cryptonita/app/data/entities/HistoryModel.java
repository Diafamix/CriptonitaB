package com.cryptonita.app.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Create History JPA entity
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@EqualsAndHashCode(of = {"id", "user"})
@Table(name = "HISTORY")
public class HistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserModel user;
    private LocalDate date;
    private String origin;
    private String destiny;
    private double quantity;

    private String portfolio;

    @Builder
    public HistoryModel(UserModel user, LocalDate date, String origin, String destiny, double quantity, String portfolio) {
        this.user = user;
        this.date = date;
        this.origin = origin;
        this.destiny = destiny;
        this.quantity = quantity;
        this.portfolio = portfolio;
    }


}
