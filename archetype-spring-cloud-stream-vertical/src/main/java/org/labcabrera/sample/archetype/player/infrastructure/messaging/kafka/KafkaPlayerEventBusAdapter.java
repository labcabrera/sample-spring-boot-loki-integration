package org.labcabrera.sample.archetype.player.infrastructure.messaging.kafka;

import org.labcabrera.sample.archetype.player.application.ports.PlayerEventBusPort;
import org.labcabrera.sample.archetype.player.domain.events.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.player.domain.events.PlayerDeletedEvent;
import org.labcabrera.sample.archetype.player.domain.events.PlayerUpdatedEvent;
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
        sendNotification("playerCreated-out-0", event);
    }

    @Override
    public void publish(PlayerUpdatedEvent event) {
        sendNotification("playerUpdated-out-0", event);
    }

    @Override
    public void publish(PlayerDeletedEvent event) {
        sendNotification("playerDeleted-out-0", event);
    }

    private void sendNotification(String binding, Object event) {
        try {
            streamBridge.send(binding, event);
        }
        catch (Exception ex) {
            log.error("Failed to publish {}", event.getClass().getSimpleName(), ex);
        }
    }

}
