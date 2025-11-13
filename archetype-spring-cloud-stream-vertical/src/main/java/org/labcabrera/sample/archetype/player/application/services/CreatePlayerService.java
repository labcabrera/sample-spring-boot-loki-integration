package org.labcabrera.sample.archetype.player.application.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.player.domain.Player;
import org.labcabrera.sample.archetype.player.domain.PlayerStatus;
import org.labcabrera.sample.archetype.shared.domain.exceptions.ConflictException;
import org.labcabrera.sample.archetype.shared.domain.exceptions.ConstraintViolationException;
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
        playerRepository.findByEmail(email).ifPresent(e -> {
            throw new ConflictException("Already existing player with email " + e.getEmail());
        });
        var player = Player.builder()
            .id(UUID.randomUUID().toString())
            .name(name)
            .email(email)
            .elo(elo != null ? elo : 1200)
            .status(PlayerStatus.UNCONFIRMED_EMAIL)
            .createdAt(LocalDateTime.now())
            .build();
        val violations = validator.validate(player);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Player entity validation failed", violations);
        }
        return playerRepository.save(player);
    }

}
