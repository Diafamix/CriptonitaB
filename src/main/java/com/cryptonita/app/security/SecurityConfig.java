package com.cryptonita.app.security;

import com.cryptonita.app.security.filters.BannedIPFilter;
import com.cryptonita.app.security.filters.BannerUserFilter;
import com.cryptonita.app.security.filters.RatePerMinuteFilter;
import com.cryptonita.app.security.filters.RatePerMonthFilter;
import com.cryptonita.app.security.handlers.AuthenticationErrorHandling;
import com.cryptonita.app.security.handlers.AuthorizationErrorHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BannerUserFilter bannerUserFilter;
    private final RatePerMinuteFilter ratePerMinuteFilter;
    private final BannedIPFilter bannedIPFilter;
    private final RatePerMonthFilter ratePerMonthFilter;

    private final AuthenticationErrorHandling authenticationErrorHandling;
    private final AuthorizationErrorHandler authorizationErrorHandler;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf()
                    .disable()
                .csrf().disable()
                .addFilterBefore(bannedIPFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(bannerUserFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(ratePerMinuteFilter, BannerUserFilter.class)
                .addFilterAfter(ratePerMonthFilter, RatePerMinuteFilter.class)
                .authorizeRequests()
                    .antMatchers("/api/**")
                    .hasAnyAuthority("ADMIN", "USER")
                .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationErrorHandling)
                .and()
                    .headers().frameOptions().disable()
                .and()
                .exceptionHandling().accessDeniedHandler(authorizationErrorHandler);
    }

}