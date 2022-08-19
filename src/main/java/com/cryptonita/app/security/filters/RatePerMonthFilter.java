package com.cryptonita.app.security.filters;

import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.data.entities.enums.UserRole;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.response.UserResponseDTO;
import com.cryptonita.app.security.SecurityContextHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class RatePerMonthFilter extends OncePerRequestFilter {

    private final SecurityContextHelper securityContextHelper;
    private final ObjectMapper jsonMapper;

    private final IUserProvider userProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        UserResponseDTO useDTO = securityContextHelper.getUser();
        if (useDTO == null || useDTO.role == UserRole.ADMIN) {
            filterChain.doFilter(request, response);
            return;
        }

        useDTO = userProvider.getByName(useDTO.getUsername());

        if(userProvider.getByName(useDTO.getUsername()).numRequests >= useDTO.type.getRateLimitPerMonth()) {
            response.setContentType("application/json");
            response.getOutputStream().print(
                    jsonMapper.writeValueAsString(
                            RestResponse.error(HttpStatus.TOO_MANY_REQUESTS.value(), "You have exceeded your monthly rate!")
                    )
            );

            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/api/");
    }

}
