package org.labcabrera.sample.archetype.player.application.cqrs.handlers;

import org.labcabrera.sample.archetype.player.application.cqrs.commands.DeletePlayerCommand;
import org.labcabrera.sample.archetype.player.application.ports.PlayerEventBusPort;
import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.player.domain.Player;
import org.labcabrera.sample.archetype.player.domain.events.PlayerDeletedEvent;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeletePlayerCommandHandler implements CommandHandler<DeletePlayerCommand, Void> {

    private final PlayerRepository playerRepository;
    private final PlayerEventBusPort playerEventBusPort;

    @Override
    public Void handle(DeletePlayerCommand command) {
        var player = playerRepository.findById(command.playerId())
            .orElseThrow(() -> new NotFoundException(command.playerId(), Player.class));
        playerRepository.deleteById(command.playerId());
        sendNotification(player);
        return null;
    }

    private void sendNotification(Player player) {
        var event = new PlayerDeletedEvent(
            player.getId(),
            player.getName(),
            player.getEmail());
        playerEventBusPort.publish(event);
    }
}
