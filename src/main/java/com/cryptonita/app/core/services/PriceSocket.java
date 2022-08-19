package com.cryptonita.app.core.services;

import com.cryptonita.app.data.providers.ICoinProvider;
import com.cryptonita.app.dto.data.response.CoinResponseDTO;
import com.cryptonita.app.integration.websocket.CoinCapPriceUpdateEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PriceSocket {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ICoinProvider coinProvider;

    @Async("clientInboundChannelExecutor")
    @EventListener
    public void listener(CoinCapPriceUpdateEvent e) {
        log.debug("Received event for coin " + e.toString());

        CoinResponseDTO responseDTO;
        try {
            responseDTO = coinProvider.getCoinByName(e.getCoin());
        } catch (Exception err) {
            return;
        }

        String topic = String.format("/crypto/%s", e.getCoin());
        simpMessagingTemplate.convertAndSend(topic, new WrapperEvent(e.getCoin(), responseDTO.symbol, e.getPrice()));
    }

    @AllArgsConstructor
    private static final class WrapperEvent {

        public final String coin;
        public final String symbol;
        public final double price;

    }

}