package com.cryptonita.app.dto.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
public class BannedUserResponseDTO {

    public final long userID;
    public final String username;
    public final LocalDateTime bannedAt;
    public final LocalDateTime expiresAt;

}
