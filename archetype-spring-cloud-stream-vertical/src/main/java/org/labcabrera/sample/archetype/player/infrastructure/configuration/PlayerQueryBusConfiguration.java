package org.labcabrera.sample.archetype.player.infrastructure.configuration;

import org.labcabrera.sample.archetype.player.application.cqrs.handlers.GetPlayerByIdQueryHandler;
import org.labcabrera.sample.archetype.player.application.cqrs.handlers.GetPlayersByRsqlQueryHandler;
import org.labcabrera.sample.archetype.player.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.player.application.cqrs.queries.GetPlayersByRsqlQuery;
import org.labcabrera.sample.archetype.shared.application.SimpleQueryBus;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class PlayerQueryBusConfiguration {

    private final SimpleQueryBus queryBus;
    private final GetPlayerByIdQueryHandler getPlayerByIdQueryHandler;
    private final GetPlayersByRsqlQueryHandler getPlayersByRsqlQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        log.info("Registering query handlers");
        queryBus.registerHandler(GetPlayerByIdQuery.class, getPlayerByIdQueryHandler);
        queryBus.registerHandler(GetPlayersByRsqlQuery.class, getPlayersByRsqlQueryHandler);
    }

}
