package org.labcabrera.sample.archetype.casestep.application.cqrs.handlers;

import org.labcabrera.sample.archetype.casestep.application.cqrs.commands.CreateInitialCaseStepCommand;
import org.labcabrera.sample.archetype.casestep.domain.CaseStep;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.labcabrera.sample.archetype.shared.application.SecurityPort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateInitialCaseStepCommandHandler implements CommandHandler<CreateInitialCaseStepCommand, CaseStep> {

    private final SecurityPort securityPort;

    @Override
    public CaseStep handle(CreateInitialCaseStepCommand command) {
        log.info("Creating initial case step for case folder {}", command.caseFolderId());
        var user = securityPort.requireCurrentUser();
        log.debug("Current user: {}", user.username());
        //TODO
        return null;
    }

}
