package com.cryptonita.app.security.filters;


import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.cryptonita.app.security.utils.LoginAttemptsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class BannedIPFilter extends OncePerRequestFilter {

    private LoginAttemptsService loginAttemptsService;
    private ObjectMapper jsonMapper;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (loginAttemptsService.isBlocked(getClientIP(request))) {
            response.setContentType("application/json");
            response.getOutputStream().print(
                    jsonMapper.writeValueAsString(
                            RestResponse.error(HttpStatus.UNAUTHORIZED.value(), "Too many login attempts!")
                    )
            );
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

}
