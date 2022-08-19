package com.cryptonita.app.dto.data.request;

import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.data.entities.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@AllArgsConstructor
@Builder
public class UserRegisterDTO {

    public String mail;
    public String username;
    public String password;
    public UserRole role;
    public UserType type;

}
