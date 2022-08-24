package com.cryptonita.app;

import com.cryptonita.app.core.controllers.services.IPortfolioService;
import com.cryptonita.app.core.loaders.CoinLoader;
import com.cryptonita.app.core.loaders.UsersLoader;
import com.cryptonita.app.core.services.IPortfolioCalculator;
import com.cryptonita.app.data.providers.IAccountProvider;
import com.cryptonita.app.data.providers.IRegisterProvider;
import com.cryptonita.app.data.providers.IUserProvider;
import com.cryptonita.app.integration.websocket.CoinCapConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

    @Bean
    CommandLineRunner init(
            CoinLoader coinLoader,
            UsersLoader usersLoader,
            IAccountProvider accountProvider,
            CoinCapConsumer coinCapConsumer,
            IRegisterProvider registerProvider,
            IPortfolioCalculator portfolioCalculator

    ) {
        return (args) -> {
            coinCapConsumer.start(); // Starts websocket

            coinLoader.load().blockLast();
            usersLoader.load().blockLast();

            mockHistoryAndPortfolio(accountProvider, registerProvider);
        };
    }

    private void mockHistoryAndPortfolio(IAccountProvider accountProvider, IRegisterProvider registerProvider) {

        accountProvider.deposit("sergio.bernal", "Bitcoin", 1);

        registerProvider.log("sergio.bernal",
                LocalDate.now().minusDays(10), "Test", "test2", 15);

        accountProvider.deposit("sergio.bernal", "Bitcoin", 1);

        registerProvider.log("sergio.bernal",
                LocalDate.now().minusDays(5), "Test", "test2", 15);

        accountProvider.deposit("sergio.bernal", "Cardano", 4.58);

        registerProvider.log("sergio.bernal",
                LocalDate.now(), "Test", "test2", 15);
    }

}
