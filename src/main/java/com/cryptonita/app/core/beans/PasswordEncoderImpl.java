package com.cryptonita.app.core.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderImpl {

    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}
