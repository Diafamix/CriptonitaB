package com.cryptonita.app.integration.websocket;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PreDestroy;

@Service
public class CoinCapConsumer {

    private static final String URL = "wss://ws.coincap.io/prices?assets=ALL";

    @Autowired
    private CoinCapPriceHandler handler;
    private WebSocketConnectionManager connectionManager;

    public void start() {
        connectionManager = new WebSocketConnectionManager(
                new StandardWebSocketClient(),
                handler,
                URL);

        connectionManager.start();
    }

    @PreDestroy
    public void stop() {
        connectionManager.stop();
    }

}
