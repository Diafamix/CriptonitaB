package com.cryptonita.app.security.handlers;

import com.cryptonita.app.core.controllers.utils.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationErrorHandling implements AuthenticationEntryPoint {

    private ObjectMapper jsonMapper;

    @SneakyThrows
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        response.setContentType("application/json");
        response.getOutputStream()
                .println(jsonMapper.writeValueAsString(
                        RestResponse.encapsulate(authException, HttpStatus.UNAUTHORIZED)
                ));
    }
}