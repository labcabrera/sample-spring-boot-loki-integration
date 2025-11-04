package org.labcabrera.sample.loki.application;

import org.axonframework.eventhandling.EventHandler;
import org.labcabrera.sample.loki.domain.player.event.PlayerCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePlayerEventHandler {

    private final PlayerQueryHandler playerQueryHandler;

    @Autowired
    private StreamBridge streamBridge;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @EventHandler
    public void on(PlayerCreatedEvent event) {
        log.info("Player created event received: playerId={}, name={}", event.getPlayerId(), event.getName());
        handlePlayerCreatedProjection(event);
        handlePlayerCreatedNotification(event);
        publishPlayerCreatedToKafka(event);
    }

    private void publishPlayerCreatedToKafka(PlayerCreatedEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            boolean sent = streamBridge.send("player-created", payload);
            if (sent) {
                log.info("Published PlayerCreatedEvent via StreamBridge to destination 'player-created' for playerId={}",
                    event.getPlayerId());
            }
            else {
                log.warn("StreamBridge returned false when sending PlayerCreatedEvent for playerId={}", event.getPlayerId());
            }
        }
        catch (JsonProcessingException e) {
            log.error("Failed to serialize PlayerCreatedEvent for publish, playerId={}", event.getPlayerId(), e);
        }
    }

    private void handlePlayerCreatedProjection(PlayerCreatedEvent event) {
        log.debug("Updating player projection for playerId: {}", event.getPlayerId());
        // Actualizar la vista de lectura
        playerQueryHandler.updatePlayerView(event.getPlayerId(), event.getName(), event.getEmail(), event.getElo());
    }

    private void handlePlayerCreatedNotification(PlayerCreatedEvent event) {
        log.debug("Sending notification for new player: {}", event.getName());
        // Aquí enviarías notificaciones, emails, etc.
    }
}