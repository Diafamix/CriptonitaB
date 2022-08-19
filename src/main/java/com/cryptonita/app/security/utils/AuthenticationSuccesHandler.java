package com.cryptonita.app.security.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationSuccesHandler implements ApplicationListener<AuthenticationSuccessEvent> {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptsService loginAttemptsService;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent event) {

        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            loginAttemptsService.loginSucceeded(request.getRemoteAddr());
        } else {
            loginAttemptsService.loginSucceeded(xfHeader.split(",")[0]);
        }
    }
}
