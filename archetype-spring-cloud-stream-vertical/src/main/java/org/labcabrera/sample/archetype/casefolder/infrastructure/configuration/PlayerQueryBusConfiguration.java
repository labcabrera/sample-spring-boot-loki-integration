package org.labcabrera.sample.archetype.casefolder.infrastructure.configuration;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers.GetCaseFolderByIdQueryHandler;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers.GetCaseHoldersByRsqlQueryHandler;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.queries.GetCaseFolderByIdQuery;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.queries.GetCaseFoldersByRsqlQuery;
import org.labcabrera.sample.archetype.shared.application.SimpleQueryBus;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class PlayerQueryBusConfiguration {

    private final SimpleQueryBus queryBus;
    private final GetCaseFolderByIdQueryHandler idQueryHandler;
    private final GetCaseHoldersByRsqlQueryHandler rsqlQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryBus.registerHandler(GetCaseFolderByIdQuery.class, idQueryHandler);
        queryBus.registerHandler(GetCaseFoldersByRsqlQuery.class, rsqlQueryHandler);
    }

}
