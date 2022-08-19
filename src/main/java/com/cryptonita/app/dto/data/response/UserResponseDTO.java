package com.cryptonita.app.dto.data.response;

import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.data.entities.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Builder
@Data
public class UserResponseDTO {

    public final String mail;
    public final String username;
    public final UserRole role;
    public final UserType type;
    public final List<FavoritesResponseDto> favorites;
    public final Map<String, WalletResponseDto> wallet;

}
