package org.labcabrera.sample.archetype.application.cqrs.handlers;

import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayerByRsqlQuery;
import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.domain.player.aggregate.Player;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerQueryByRsqlHandler {

    private final PlayerRepository playerRepository;

    public Page<Player> handle(GetPlayerByRsqlQuery query) {
        log.debug("Handling RSQL query for player <<< {}", query.getRsql());
        return playerRepository.findByRsql(query.getRsql(), query.getPageable());
    }
}
