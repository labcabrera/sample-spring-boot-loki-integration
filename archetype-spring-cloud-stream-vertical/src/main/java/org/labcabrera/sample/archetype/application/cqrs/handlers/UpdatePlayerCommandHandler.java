package org.labcabrera.sample.archetype.application.cqrs.handlers;

import org.labcabrera.sample.archetype.application.cqrs.commands.UpdatePlayerCommand;
import org.labcabrera.sample.archetype.application.ports.PlayerEventBusPort;
import org.labcabrera.sample.archetype.application.services.UpdatePlayerService;
import org.labcabrera.sample.archetype.domain.player.Player;
import org.labcabrera.sample.archetype.domain.player.event.PlayerUpdatedEvent;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdatePlayerCommandHandler implements CommandHandler<UpdatePlayerCommand, Player> {

    private final UpdatePlayerService updatePlayerService;
    private final PlayerEventBusPort playerEventBusPort;

    public Player handle(UpdatePlayerCommand command) {
        log.info("Update player << {}", command.playerId());
        var player = updatePlayerService.updatePlayer(command.playerId(), command.name(), command.status());
        sendNotification(player);
        return player;
    }

    private void sendNotification(Player player) {
        var event = new PlayerUpdatedEvent(
            player.getId(),
            player.getName(),
            player.getEmail(),
            player.getStatus());
        playerEventBusPort.publish(event);
    }
}
