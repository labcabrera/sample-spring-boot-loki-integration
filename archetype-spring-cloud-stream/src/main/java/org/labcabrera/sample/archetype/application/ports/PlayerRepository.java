package org.labcabrera.sample.archetype.application.ports;

import org.labcabrera.sample.archetype.domain.player.aggregate.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> findById(String playerId);

    Optional<Player> findByEmail(String email);

    Page<Player> findByRsql(String rsql, Pageable pageable);

    List<Player> findByEloRange(Integer minElo, Integer maxElo);

    Player save(@NotNull Player entity);

    Player update(@NotNull Player entity);

    List<Player> findAll();
}