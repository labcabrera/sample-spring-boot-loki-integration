package org.labcabrera.sample.archetype.interfaces.kafka;

import java.util.function.Consumer;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.labcabrera.sample.archetype.domain.player.command.CreatePlayerCommand;
import org.labcabrera.sample.archetype.domain.player.event.PlayerCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KafkaCreatePlayerController {

    @Autowired
    private CommandGateway commandGateway;

    @Bean
    public Consumer<PlayerCreatedEvent> createPlayer() {
        return event -> {
            log.info("Player created << {}", event);
            var command = new CreatePlayerCommand(
                event.getPlayerId(),
                event.getName(),
                event.getEmail(),
                event.getElo());
            commandGateway.send(command);
        };
    }
}
