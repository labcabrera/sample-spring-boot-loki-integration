package org.labcabrera.sample.archetype.interfaces.kafka;

import java.util.function.Consumer;

import org.axonframework.eventhandling.gateway.EventGateway;
import org.labcabrera.sample.archetype.domain.player.event.PlayerCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaCreatePlayerController {

    @Autowired
    private EventGateway eventGateway;

    @Bean
    public Consumer<PlayerCreatedEvent> createPlayer() {
        return event -> {
            log.info("Received PlayerCreatedEvent from Kafka, re-publishing into Axon event bus: {}", event);
            eventGateway.publish(event);
        };
    }
}
