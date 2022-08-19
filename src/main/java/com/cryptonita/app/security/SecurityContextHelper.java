package com.cryptonita.app.security;

import com.cryptonita.app.dto.data.response.UserResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextHelper {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserResponseDTO getUser() {
        return (UserResponseDTO) getAuthentication().getPrincipal();
    }

}