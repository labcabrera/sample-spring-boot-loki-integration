package org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.UpdateCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderEventBusPort;
import org.labcabrera.sample.archetype.casefolder.application.services.UpdateCaseFolderService;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderUpdatedEvent;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateCaseFolderCommandHandler implements CommandHandler<UpdateCaseFolderCommand, CaseFolder> {

    private final UpdateCaseFolderService updateCaseFolderService;
    private final CaseFolderEventBusPort caseFolderEventBusPort;

    public CaseFolder handle(UpdateCaseFolderCommand command) {
        log.info("Update case folder << {}", command.caseFolderId());
        var caseFolder = updateCaseFolderService.updateCaseFolder(
            command.caseFolderId(),
            command.name(),
            command.firstSurname(),
            command.lastSurname());
        sendNotification(caseFolder);
        return caseFolder;
    }

    private void sendNotification(CaseFolder caseFolder) {
        var event = new CaseFolderUpdatedEvent(
            caseFolder.getId(),
            caseFolder.getName(),
            caseFolder.getFirstSurname(),
            caseFolder.getLastSurname());
        caseFolderEventBusPort.publish(event);
    }
}
