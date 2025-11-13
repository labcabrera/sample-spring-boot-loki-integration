package org.labcabrera.sample.archetype.player.application.ports;

import org.labcabrera.sample.archetype.domain.player.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlayerRepository {

    Optional<Player> findById(String playerId);

    Optional<Player> findByEmail(String email);

    Page<Player> findByRsql(String rsql, Pageable pageable);

    Player save(Player entity);

    Player update(Player entity);

}