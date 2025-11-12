package org.labcabrera.sample.archetype.application.services;

import java.util.UUID;

import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.domain.player.aggregate.Player;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final Validator validator;

    public Player createPlayer(String name, String email, Integer elo) {
        var player = Player.builder()
            .id(UUID.randomUUID().toString())
            .name(name)
            .email(email)
            .elo(elo)
            .build();
        val violations = validator.validate(player);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Player entity validation failed: " + violations);
        }
        return playerRepository.save(player);
    }

    public void updatePlayer(String playerId, String name, String email) {
        // Optionally validate update (e.g., email uniqueness) here
        // commandGateway.sendAndWait(new UpdatePlayerCommand(playerId, name, email));
    }
}
