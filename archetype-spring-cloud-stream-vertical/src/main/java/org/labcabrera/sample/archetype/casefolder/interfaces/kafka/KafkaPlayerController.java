package org.labcabrera.sample.archetype.casefolder.interfaces.kafka;

import java.util.function.Consumer;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.CreateCaseFolderCommand;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class KafkaPlayerController {

    private final CommandBus commandBus;

    @Bean
    public Consumer<CreateCaseFolderCommand> processPlayerCreation() {
        return command -> {
            log.info("Received PlayerCreatedEvent from Kafka: {}", command);
            commandBus.dispatch(command);
        };
    }
}
