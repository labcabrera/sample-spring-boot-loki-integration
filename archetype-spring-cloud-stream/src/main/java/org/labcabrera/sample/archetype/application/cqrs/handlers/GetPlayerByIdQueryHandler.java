package org.labcabrera.sample.archetype.application.cqrs.handlers;

import org.labcabrera.sample.archetype.application.cqrs.QueryHandler;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.domain.player.Player;
import org.labcabrera.sample.archetype.domain.player.exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPlayerByIdQueryHandler implements QueryHandler<GetPlayerByIdQuery, Player> {

    private final PlayerRepository playerRepository;

    public Player handle(GetPlayerByIdQuery query) {
        log.debug("Getting player by id << {}", query.playerId());
        return playerRepository
            .findById(query.playerId())
            .orElseThrow(() -> new NotFoundException(query.playerId(), Player.class));
    }

}