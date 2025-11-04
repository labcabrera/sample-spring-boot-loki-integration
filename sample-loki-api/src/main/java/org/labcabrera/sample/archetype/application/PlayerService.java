package org.labcabrera.sample.archetype.application;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.labcabrera.sample.archetype.domain.player.command.CreatePlayerCommand;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final CommandGateway commandGateway;
    private final PlayerValidationService validationService;

    public String createPlayer(String name, String email, Integer elo) {
        // Validar antes de crear
        validationService.validatePlayerCreation(email);

        String id = UUID.randomUUID().toString();
        commandGateway.sendAndWait(new CreatePlayerCommand(id, name, email, elo));
        return id;
    }
}
