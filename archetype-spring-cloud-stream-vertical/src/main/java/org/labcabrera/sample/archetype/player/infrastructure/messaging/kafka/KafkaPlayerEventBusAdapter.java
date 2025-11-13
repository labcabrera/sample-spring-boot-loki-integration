package org.labcabrera.sample.archetype.player.infrastructure.messaging.kafka;

import org.labcabrera.sample.archetype.player.application.ports.PlayerEventBusPort;
import org.labcabrera.sample.archetype.player.domain.event.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.player.domain.event.PlayerUpdatedEvent;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaPlayerEventBusAdapter implements PlayerEventBusPort {

    private final StreamBridge streamBridge;

    @Override
    public void publish(PlayerCreatedEvent event) {
        try {
            streamBridge.send("playerCreated-out-0", event);
        }
        catch (Exception ex) {
            log.error("Failed to publish PlayerCreatedEvent for {}", event.playerId(), ex);
        }
    }

    @Override
    public void publish(PlayerUpdatedEvent event) {
        try {
            streamBridge.send("player-updated", event);
        }
        catch (Exception ex) {
            log.error("Failed to publish PlayerUpdatedEvent for {}", event.playerId(), ex);
        }
    }

}
