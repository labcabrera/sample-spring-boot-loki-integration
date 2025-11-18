package org.labcabrera.sample.archetype.confirmation.infrastructure.configuration;

import org.labcabrera.sample.archetype.confirmation.application.cqrs.commands.PlayerConfirmationCommand;
import org.labcabrera.sample.archetype.confirmation.application.cqrs.handlers.PlayerConfirmationCommandHandler;
import org.labcabrera.sample.archetype.shared.application.SimpleCommandBus;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PlayerConfirmationCommandBusConfiguration {

    private final SimpleCommandBus commandBus;
    private final PlayerConfirmationCommandHandler playerConfirmationCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        commandBus.registerHandler(PlayerConfirmationCommand.class, playerConfirmationCommandHandler);
    }

}