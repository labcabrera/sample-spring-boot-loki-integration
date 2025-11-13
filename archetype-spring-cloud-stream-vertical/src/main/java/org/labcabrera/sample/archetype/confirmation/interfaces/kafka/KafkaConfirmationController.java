package org.labcabrera.sample.archetype.confirmation.interfaces.kafka;

import java.util.function.Consumer;

import org.labcabrera.sample.archetype.player.domain.events.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaConfirmationController {

    private final CommandBus commandBus;

    @Bean
    public Consumer<PlayerCreatedEvent> processConfirmationOnPlayerCreation() {
        return event -> {
            log.info("Received PlayerCreatedEvent from Kafka: {}", event);
        };
    }
}