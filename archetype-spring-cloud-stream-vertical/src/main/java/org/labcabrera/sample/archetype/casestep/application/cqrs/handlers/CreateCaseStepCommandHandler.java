package org.labcabrera.sample.archetype.casestep.application.cqrs.handlers;

import org.labcabrera.sample.archetype.casestep.application.cqrs.commands.CreateCaseStepCommand;
import org.labcabrera.sample.archetype.casestep.domain.CaseStep;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CreateCaseStepCommandHandler implements CommandHandler<CreateCaseStepCommand, CaseStep> {

    @Override
    public CaseStep handle(CreateCaseStepCommand command) {
        log.info("Handling CreateCaseStepCommand: {}", command);
        //TODO
        return null;
    }

}
