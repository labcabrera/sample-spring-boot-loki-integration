package org.labcabrera.sample.archetype.casestep.interfaces.kafka;

import java.util.function.Consumer;

import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderCreatedEvent;
import org.labcabrera.sample.archetype.casestep.application.cqrs.commands.CreateCaseStepCommand;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaCaseStepController {

    private final CommandBus commandBus;

    @Bean
    public Consumer<CaseFolderCreatedEvent> processCaseStepCreation() {
        return event -> commandBus.dispatch(new CreateCaseStepCommand(event.id()));
    }
}
