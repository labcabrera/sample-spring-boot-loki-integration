package org.labcabrera.sample.archetype.confirmation.application.cqrs.handlers;

import org.labcabrera.sample.archetype.confirmation.application.cqrs.commands.PlayerConfirmationCommand;
import org.labcabrera.sample.archetype.confirmation.domain.PlayerConfirmation;
import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerConfirmationCommandHandler implements CommandHandler<PlayerConfirmationCommand, PlayerConfirmation> {

    private final PlayerRepository playerRepository;

    @Override
    public PlayerConfirmation handle(PlayerConfirmationCommand command) {
        log.info("Creating player email confirmation << {}:{}", command.playerId(), command.email());
        return null;
    }

}