package org.labcabrera.sample.archetype.casestep.infrastructure.configuration;

import org.labcabrera.sample.archetype.casestep.application.cqrs.handlers.GetCaseStepByIdQueryHandler;
import org.labcabrera.sample.archetype.casestep.application.cqrs.handlers.GetCaseStepsByCaseFolderIdQueryHandler;
import org.labcabrera.sample.archetype.casestep.application.cqrs.queries.GetCaseStepByIdQuery;
import org.labcabrera.sample.archetype.casestep.application.cqrs.queries.GetCaseStepsByCaseFolderIdQuery;
import org.labcabrera.sample.archetype.shared.application.SimpleQueryBus;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CaseStepQueryBusConfiguration {

    private final SimpleQueryBus queryBus;
    private final GetCaseStepByIdQueryHandler idQueryHandler;
    private final GetCaseStepsByCaseFolderIdQueryHandler caseFolderIdQueryHandler;

    @PostConstruct
    public void registerHandlers() {
        queryBus.registerHandler(GetCaseStepByIdQuery.class, idQueryHandler);
        queryBus.registerHandler(GetCaseStepsByCaseFolderIdQuery.class, caseFolderIdQueryHandler);

    }
}
