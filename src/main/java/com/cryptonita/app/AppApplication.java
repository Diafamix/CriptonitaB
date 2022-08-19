package com.cryptonita.app;

import com.cryptonita.app.core.controllers.services.IPortfolioService;
import com.cryptonita.app.core.loaders.CoinLoader;
import com.cryptonita.app.core.loaders.UsersLoader;
import com.cryptonita.app.data.providers.IAccountProvider;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.dto.data.request.RegisterRequestDTO;
import com.cryptonita.app.integration.websocket.CoinCapConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.time.LocalDate;

@Slf4j
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    CommandLineRunner init(
            CoinLoader coinLoader,
            UsersLoader usersLoader,
            IAccountProvider accountProvider,
            IPortfolioService portfolioService,
            IUserProvider userProvider,
            CoinCapConsumer coinCapConsumer
    ) {
        return (args) -> {
            coinCapConsumer.start(); // Starts websocket

            coinLoader.load().blockLast();
            usersLoader.load().blockLast();

            accountProvider.deposit("sergio.bernal", "Bitcoin", 12);

            accountProvider.deposit("sergio.bernal", "ethereum", 120);

            RegisterRequestDTO registerRequestDTO = RegisterRequestDTO.builder()
                    .date(LocalDate.now())
                    .quantity(12)
                    .destiny("Mi wallet")
                    .origin("Mi cartera")
                    .user("sergio.bernal")
                    .build();


            //registerProvider.log(registerRequestDTO);

            userProvider.banUser("antonio.borja@optimissa.com");
            log.info("" + userProvider.isBannedByUsername("antonio.borja"));

        };
    }

    private void callBack() {
    }

}