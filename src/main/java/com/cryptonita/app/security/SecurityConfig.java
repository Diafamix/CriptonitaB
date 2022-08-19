package com.cryptonita.app.security;

import com.cryptonita.app.security.filters.BannerUserFilter;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private BannerUserFilter bannerUserFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                    .and()
                .csrf().disable()
                .addFilterAfter(bannerUserFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/**")
                .hasAnyAuthority("ADMIN", "USER")
                .and()
                .httpBasic()
                .and()
                .headers().frameOptions().disable();
    }

}