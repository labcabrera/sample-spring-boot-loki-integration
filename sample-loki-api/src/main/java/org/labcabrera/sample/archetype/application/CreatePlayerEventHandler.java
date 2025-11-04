package org.labcabrera.sample.archetype.application;

import org.axonframework.eventhandling.EventHandler;
import org.labcabrera.sample.archetype.domain.player.event.PlayerCreatedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePlayerEventHandler {

    private final StreamBridge streamBridge;
    private final PlayerService playerService;

    @EventHandler
    public void on(PlayerCreatedEvent event) {
        log.info("Player created event received: playerId={}, name={}", event.getPlayerId(), event.getName());
        createPlayer(event);
        sendNotification(event);
    }

    private void createPlayer(PlayerCreatedEvent event) {
        log.debug("Creating player: {}", event.getName());
        playerService.createPlayer(event.getName(), event.getEmail(), event.getElo());
    }

    private void sendNotification(PlayerCreatedEvent event) {
        try {
            streamBridge.send("player-created", event);
        }
        catch (Exception e) {
            log.error("Failed to publish PlayerCreatedEvent, playerId={}", event.getPlayerId(), e);
        }
    }

}