package org.labcabrera.sample.archetype.configuration;

import org.labcabrera.sample.archetype.player.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.commands.UpdatePlayerCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.handlers.CreatePlayerCommandHandler;
import org.labcabrera.sample.archetype.player.application.cqrs.handlers.UpdatePlayerCommandHandler;
import org.labcabrera.sample.archetype.shared.application.SimpleCommandBus;
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
