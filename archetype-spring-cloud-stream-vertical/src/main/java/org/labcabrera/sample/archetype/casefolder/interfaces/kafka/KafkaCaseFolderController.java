package org.labcabrera.sample.archetype.casefolder.interfaces.kafka;

import java.util.function.Consumer;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.CreateCaseFolderCommand;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaCaseFolderController {

    private final CommandBus commandBus;

    @Bean
    public Consumer<CreateCaseFolderCommand> processCaseFolderCreation() {
        return command -> commandBus.dispatch(command);
    }
}
