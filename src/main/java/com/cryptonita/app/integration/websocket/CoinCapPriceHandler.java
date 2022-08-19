package com.cryptonita.app.integration.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@AllArgsConstructor
public class CoinCapPriceHandler extends TextWebSocketHandler {

    private final CoinCapJsonParser parser;
    private final ApplicationEventPublisher publisher;

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.debug("Message Received [" + message.getPayload() + "]");

        parser.parse(message.getPayload()).forEach(coinUpdatePair -> {

            CoinCapPriceUpdateEvent e = new CoinCapPriceUpdateEvent(
                    this,
                    coinUpdatePair.coin,
                    coinUpdatePair.price
            );

            publisher.publishEvent(e);
        });

        /*MessageProperties props = new MessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        rabbitTemplate.send("ada", converter.toMessage(message.getPayload(), props)); */
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        session.setTextMessageSizeLimit(30000);
        String payload = "{\"type\": \"subscribe\",\"channels\":[{\"name\": \"ticker\",\"product_ids\": [\"ETH-EUR\"]}]}";
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("Transport Error", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Connection Closed [" + status.getReason() + "]");
    }

}