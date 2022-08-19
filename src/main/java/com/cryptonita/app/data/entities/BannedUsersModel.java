package com.cryptonita.app.data.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ={"id", "user"})
@Table(name = "BannedUsers")
public class BannedUsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private UserModel user;

    private LocalDateTime bannedAt;

    private LocalDateTime expiresAt;

    @Builder
    public BannedUsersModel(LocalDateTime bannedAt, LocalDateTime expiresAt, UserModel user) {
        this.bannedAt = bannedAt;
        this.expiresAt = expiresAt;
        this.user = user;
    }

}
