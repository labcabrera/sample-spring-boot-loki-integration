package org.labcabrera.sample.archetype.confirmation.infrastructure.configuration;

import org.labcabrera.sample.archetype.confirmation.application.cqrs.commands.PlayerConfirmationCommand;
import org.labcabrera.sample.archetype.confirmation.application.cqrs.handlers.PlayerConfirmationCommandHandler;
import org.labcabrera.sample.archetype.shared.application.SimpleCommandBus;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PlayerConfirmationCommandBusConfiguration {

    private final SimpleCommandBus commandBus;
    private final PlayerConfirmationCommandHandler playerConfirmationCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        log.info("Registering command handlers");
        commandBus.registerHandler(PlayerConfirmationCommand.class, playerConfirmationCommandHandler);
    }

}