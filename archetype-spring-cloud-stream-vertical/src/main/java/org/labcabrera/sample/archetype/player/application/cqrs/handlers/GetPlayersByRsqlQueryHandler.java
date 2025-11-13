package org.labcabrera.sample.archetype.player.application.cqrs.handlers;

import org.labcabrera.sample.archetype.player.application.cqrs.queries.GetPlayersByRsqlQuery;
import org.labcabrera.sample.archetype.player.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.player.domain.Player;
import org.labcabrera.sample.archetype.shared.application.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPlayersByRsqlQueryHandler implements QueryHandler<GetPlayersByRsqlQuery, Page<Player>> {

    private final PlayerRepository playerRepository;

    public Page<Player> handle(GetPlayersByRsqlQuery query) {
        log.debug("Handling RSQL query for player <<< {}", query.rsql());
        return playerRepository.findByRsql(query.rsql(), query.pageable());
    }
}
