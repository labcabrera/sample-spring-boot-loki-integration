package org.labcabrera.sample.archetype.application.cqrs.handlers;

import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.domain.player.aggregate.Player;
import org.labcabrera.sample.archetype.domain.player.exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPlayerByIdQueryHandler {

    private final PlayerRepository playerRepository;

    public Player handle(GetPlayerByIdQuery query) {
        log.debug("Getting player by id << {}", query.getPlayerId());
        return playerRepository
            .findById(query.getPlayerId())
            .orElseThrow(() -> new NotFoundException(query.getPlayerId(), Player.class));
    }

}