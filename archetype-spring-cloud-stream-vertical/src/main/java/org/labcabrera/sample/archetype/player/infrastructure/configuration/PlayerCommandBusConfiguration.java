package org.labcabrera.sample.archetype.player.infrastructure.configuration;

import org.labcabrera.sample.archetype.player.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.commands.DeletePlayerCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.commands.ResendConfirmationCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.commands.UpdatePlayerCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.handlers.CreatePlayerCommandHandler;
import org.labcabrera.sample.archetype.player.application.cqrs.handlers.DeletePlayerCommandHandler;
import org.labcabrera.sample.archetype.player.application.cqrs.handlers.ResendConfirmationCommandHandler;
import org.labcabrera.sample.archetype.player.application.cqrs.handlers.UpdatePlayerCommandHandler;
import org.labcabrera.sample.archetype.shared.application.SimpleCommandBus;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PlayerCommandBusConfiguration {

    private final SimpleCommandBus commandBus;
    private final CreatePlayerCommandHandler createPlayerCommandHandler;
    private final UpdatePlayerCommandHandler updatePlayerCommandHandler;
    private final DeletePlayerCommandHandler deletePlayerCommandHandler;
    private final ResendConfirmationCommandHandler resendConfirmationCommandHandler;

    @PostConstruct
    public void registerHandlers() {
        commandBus.registerHandler(CreatePlayerCommand.class, createPlayerCommandHandler);
        commandBus.registerHandler(UpdatePlayerCommand.class, updatePlayerCommandHandler);
        commandBus.registerHandler(DeletePlayerCommand.class, deletePlayerCommandHandler);
        commandBus.registerHandler(ResendConfirmationCommand.class, resendConfirmationCommandHandler);
    }

}
