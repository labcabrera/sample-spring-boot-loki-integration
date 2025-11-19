package org.labcabrera.sample.archetype.casestep.infrastructure.configuration;

import org.labcabrera.sample.archetype.casestep.application.cqrs.commands.CreateInitialCaseStepCommand;
import org.labcabrera.sample.archetype.casestep.application.cqrs.handlers.CreateInitialCaseStepCommandHandler;
import org.labcabrera.sample.archetype.shared.application.SimpleCommandBus;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CaseStepCommandBusConfiguration {

    private final SimpleCommandBus commandBus;
    private final CreateInitialCaseStepCommandHandler createHandler;

    @PostConstruct
    public void registerHandlers() {
        commandBus.registerHandler(CreateInitialCaseStepCommand.class, createHandler);
    }

}
