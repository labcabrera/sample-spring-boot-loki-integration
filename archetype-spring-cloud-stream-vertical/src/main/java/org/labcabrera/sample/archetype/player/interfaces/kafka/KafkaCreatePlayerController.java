package org.labcabrera.sample.archetype.player.interfaces.kafka;

import java.util.function.Consumer;

import org.labcabrera.sample.archetype.player.domain.event.PlayerCreatedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class KafkaCreatePlayerController {

    @Bean
    public Consumer<PlayerCreatedEvent> createPlayer() {
        return event -> {
            log.info("Received PlayerCreatedEvent from Kafka: {}", event);
            //TODO
        };
    }
}
