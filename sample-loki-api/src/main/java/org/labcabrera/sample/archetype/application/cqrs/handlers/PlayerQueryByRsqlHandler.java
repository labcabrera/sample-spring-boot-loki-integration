package org.labcabrera.sample.archetype.application.cqrs.handlers;

import org.apache.commons.lang3.NotImplementedException;
import org.axonframework.queryhandling.QueryHandler;
import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.labcabrera.sample.archetype.domain.player.query.GetPlayerByRsqlQuery;
import org.labcabrera.sample.archetype.domain.player.query.PlayerView;
import org.labcabrera.sample.archetype.infrastructure.persistence.mapper.PlayerMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerQueryByRsqlHandler {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @QueryHandler
    public PlayerView handle(GetPlayerByRsqlQuery query) {
        log.debug("Handling RSQL query for player: {}", query.getRsql());
        throw new NotImplementedException("RSQL query handling is not implemented yet");
    }
}
