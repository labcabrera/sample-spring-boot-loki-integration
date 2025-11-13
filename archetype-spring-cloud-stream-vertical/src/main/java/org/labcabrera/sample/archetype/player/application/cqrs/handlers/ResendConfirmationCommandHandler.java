package org.labcabrera.sample.archetype.player.application.cqrs.handlers;

import org.labcabrera.sample.archetype.confirmation.application.cqrs.commands.PlayerConfirmationCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.commands.ResendConfirmationCommand;
import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.player.domain.Player;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResendConfirmationCommandHandler implements CommandHandler<ResendConfirmationCommand, Void> {

    private final PlayerRepository playerRepository;
    private final CommandBus commandBus;

    @Override
    public Void handle(ResendConfirmationCommand command) {
        log.debug("Processing resend confirmation << {}", command.playerId());
        var player = playerRepository.findById(command.playerId())
            .orElseThrow(() -> new NotFoundException(command.playerId(), Player.class));
        var confirmationCommand = new PlayerConfirmationCommand(player.getEmail());
        commandBus.dispatch(confirmationCommand);
        return null;
    }

}
