package com.cryptonita.app.data.entities;

import lombok.*;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Restart")
public class RestartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "Months")
    private String month;

    @Column(name = "Years")
    private String year;
}
