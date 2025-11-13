package org.labcabrera.sample.archetype.player.application.cqrs.handlers;

import org.labcabrera.sample.archetype.domain.player.Player;
import org.labcabrera.sample.archetype.domain.player.event.PlayerCreatedEvent;
import org.labcabrera.sample.archetype.player.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.player.application.ports.PlayerEventBusPort;
import org.labcabrera.sample.archetype.player.application.services.CreatePlayerService;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePlayerCommandHandler implements CommandHandler<CreatePlayerCommand, Player> {

    private final CreatePlayerService createPlayerService;
    private final PlayerEventBusPort playerEventBusPort;

    public Player handle(CreatePlayerCommand command) {
        log.info("Create player << {}", command.email());
        var player = createPlayer(command);
        sendNotification(player);
        return player;
    }

    private Player createPlayer(CreatePlayerCommand command) {
        log.debug("Creating player: {}", command.name());
        return createPlayerService.createPlayer(command.name(), command.email(), command.elo());
    }

    private void sendNotification(Player player) {
        var event = new PlayerCreatedEvent(
            player.getId(),
            player.getName(),
            player.getEmail(),
            player.getElo());
        playerEventBusPort.publish(event);
    }

}