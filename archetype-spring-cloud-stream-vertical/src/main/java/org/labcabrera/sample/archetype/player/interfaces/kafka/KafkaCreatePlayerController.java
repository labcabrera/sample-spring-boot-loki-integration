package org.labcabrera.sample.archetype.player.interfaces.kafka;

import java.util.function.Consumer;

import org.labcabrera.sample.archetype.player.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaCreatePlayerController {

    private final CommandBus commandBus;

    @Bean
    public Consumer<CreatePlayerCommand> createPlayer() {
        return event -> {
            log.info("Received PlayerCreatedEvent from Kafka: {}", event);
            commandBus.dispatch(event);
        };
    }
}
