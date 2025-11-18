package org.labcabrera.sample.archetype.player.application.cqrs.handlers;

import org.labcabrera.sample.archetype.player.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.player.domain.Player;
import org.labcabrera.sample.archetype.shared.application.QueryHandler;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotFoundException;
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