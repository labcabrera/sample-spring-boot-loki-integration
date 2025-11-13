package org.labcabrera.sample.archetype.application.services;

import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.domain.player.aggregate.Player;
import org.labcabrera.sample.archetype.domain.player.exceptions.BadRequestException;
import org.labcabrera.sample.archetype.domain.player.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePlayerService {

    private final PlayerRepository playerRepository;
    private final Validator validator;

    public Player updatePlayer(String playerId, String name) {
        var player = playerRepository.findById(playerId)
            .orElseThrow(() -> new NotFoundException(playerId, Player.class));
        player.setName(name);
        var violations = validator.validate(player);
        if (!violations.isEmpty()) {
            throw new BadRequestException("Player entity validation failed: " + violations);
        }
        return playerRepository.save(player);
    }

}
