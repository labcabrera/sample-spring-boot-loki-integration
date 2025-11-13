package org.labcabrera.sample.archetype.application.cqrs.handlers;

import org.labcabrera.sample.archetype.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.application.services.CreatePlayerService;
import org.labcabrera.sample.archetype.domain.player.aggregate.Player;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreatePlayerCommandHandler {

    private final StreamBridge streamBridge;
    private final CreatePlayerService playerService;

    public Player handle(CreatePlayerCommand command) {
        log.info("Create player << {}", command.email());
        var player = createPlayer(command);
        sendNotification(command);
        return player;
    }

    private Player createPlayer(CreatePlayerCommand command) {
        log.debug("Creating player: {}", command.name());
        return playerService.createPlayer(command.name(), command.email(), command.elo());
    }

    private void sendNotification(CreatePlayerCommand command) {
        try {
            streamBridge.send("player-created", command);
        }
        catch (Exception ex) {
            log.error("Failed to publish PlayerCreatedEvent for {}", command.email(), ex);
        }
    }

}