package org.labcabrera.sample.archetype.player.application.services;

import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.player.domain.Player;
import org.labcabrera.sample.archetype.player.domain.PlayerStatus;
import org.labcabrera.sample.archetype.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdatePlayerService {

    private final PlayerRepository playerRepository;
    private final Validator validator;

    public Player updatePlayer(String playerId, String name, PlayerStatus status) {
        var player = playerRepository.findById(playerId)
            .orElseThrow(() -> new NotFoundException(playerId, Player.class));
        if (name != null) {
            player.setName(name);
        }
        if (status != null) {
            player.setStatus(status);
        }
        var violations = validator.validate(player);
        if (!violations.isEmpty()) {
            throw new BadRequestException("Player entity validation failed: " + violations);
        }
        return playerRepository.save(player);
    }

}
