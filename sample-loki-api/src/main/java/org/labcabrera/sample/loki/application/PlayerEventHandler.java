package org.labcabrera.sample.loki.application;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.concurrent.CompletableFuture;
import org.springframework.kafka.support.SendResult;
import org.labcabrera.sample.loki.domain.player.event.PlayerCreatedEvent;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerEventHandler {

    private final PlayerQueryHandler playerQueryHandler;

    // Optional Kafka template: injected only when spring-kafka is on the classpath and
    // kafka producer properties are configured (or when beans are created in KafkaIntegrationConfiguration).
    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @EventHandler
    public void on(PlayerCreatedEvent event) {
        log.info("Player created event received: playerId={}, name={}",
            event.getPlayerId(), event.getName());

    // You can add logic here for:
    // - Updating projections / read views
    // - Sending notifications
    // - Integrating with other systems
    // - Publishing to Kafka (if available)

        handlePlayerCreatedProjection(event);
        handlePlayerCreatedNotification(event);

        // Publish event to Kafka (non-blocking). If Kafka is not configured, this is a noop.
        publishPlayerCreatedToKafka(event);
    }

    private void publishPlayerCreatedToKafka(PlayerCreatedEvent event) {
        if (kafkaTemplate == null) {
            log.debug("KafkaTemplate not configured; skipping Kafka publish for playerId={}", event.getPlayerId());
            return;
        }

        try {
            String payload = objectMapper.writeValueAsString(event);
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("player-created", event.getPlayerId(), payload);
            future.whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Failed to publish PlayerCreatedEvent to Kafka for playerId={}", event.getPlayerId(), ex);
                    return;
                }
                if (result != null && result.getRecordMetadata() != null) {
                    log.info("Published PlayerCreatedEvent to Kafka topic 'player-created' partition={} offset={}",
                        result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                } else {
                    log.info("Published PlayerCreatedEvent to Kafka topic 'player-created' (no metadata)");
                }
            });
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize PlayerCreatedEvent for Kafka publish, playerId={}", event.getPlayerId(), e);
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