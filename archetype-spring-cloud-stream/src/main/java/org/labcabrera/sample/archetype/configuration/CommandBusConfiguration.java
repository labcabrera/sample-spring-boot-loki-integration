package org.labcabrera.sample.archetype.configuration;

import org.labcabrera.sample.archetype.application.cqrs.SimpleCommandBus;
import org.labcabrera.sample.archetype.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.application.cqrs.commands.UpdatePlayerCommand;
import org.labcabrera.sample.archetype.application.cqrs.handlers.CreatePlayerCommandHandler;
import org.labcabrera.sample.archetype.application.cqrs.handlers.UpdatePlayerCommandHandler;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CommandBusConfiguration {

    private final SimpleCommandBus commandBus;
    private final CreatePlayerCommandHandler createPlayerCommandHandler;
    private final UpdatePlayerCommandHandler updatePlayerCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        log.info("Registering command handlers");
        commandBus.registerHandler(CreatePlayerCommand.class, createPlayerCommandHandler);
        commandBus.registerHandler(UpdatePlayerCommand.class, updatePlayerCommandHandler);
    }

}
