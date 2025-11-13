package org.labcabrera.sample.archetype.application.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.domain.player.aggregate.Player;
import org.labcabrera.sample.archetype.domain.player.exceptions.BadRequestException;
import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity.PlayerStatus;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class CreatePlayerService {

    private final PlayerRepository playerRepository;
    private final Validator validator;

    public Player createPlayer(String name, String email, Integer elo) {
        var current = this.playerRepository.findByEmail(email);
        if (current.isPresent()) {
            throw new BadRequestException("Email address already in use: " + email);
        }
        var player = Player.builder()
            .id(UUID.randomUUID().toString())
            .name(name)
            .email(email)
            .elo(elo)
            .status(PlayerStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();
        val violations = validator.validate(player);
        if (!violations.isEmpty()) {
            throw new BadRequestException("Player entity validation failed: " + violations);
        }
        return playerRepository.save(player);
    }

}
