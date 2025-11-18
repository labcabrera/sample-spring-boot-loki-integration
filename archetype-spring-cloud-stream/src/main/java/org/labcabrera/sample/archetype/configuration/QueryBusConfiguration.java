package org.labcabrera.sample.archetype.configuration;

import org.labcabrera.sample.archetype.application.cqrs.SimpleQueryBus;
import org.labcabrera.sample.archetype.application.cqrs.handlers.GetPlayerByIdQueryHandler;
import org.labcabrera.sample.archetype.application.cqrs.handlers.GetPlayersByRsqlQueryHandler;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayersByRsqlQuery;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class QueryBusConfiguration {

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
