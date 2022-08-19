package com.cryptonita.app.security.filters;

import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.response.BannedUserResponseDTO;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.security.SecurityContextHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class BannerUserFilter extends OncePerRequestFilter {

    private IUserProvider userProvider;
    private final SecurityContextHelper contextHelper;
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        UserResponseDTO dtoUser = contextHelper.getUser();
        if (dtoUser == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!userProvider.isBannedByUsername(dtoUser.getUsername())) {
            filterChain.doFilter(request, response);
            return;
        }

        BannedUserResponseDTO dtoBanner = userProvider.banUserByUsername(dtoUser.getUsername());
        if (dtoBanner.expiresAt.isBefore(LocalDateTime.now())) {
            userProvider.unbanUserByUsername(dtoUser.getUsername());
            filterChain.doFilter(request, response);
            return;
        }

        response.setContentType("application/json");
        response.getOutputStream().print(
                objectMapper.writeValueAsString(
                        RestResponse.encapsulate(
                                Pair.of("result", "This user is banned")
                        )
                )
        );
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/api/");
    }
}

