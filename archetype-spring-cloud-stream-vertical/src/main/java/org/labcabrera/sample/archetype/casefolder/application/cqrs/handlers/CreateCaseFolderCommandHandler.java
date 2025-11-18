package org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.CreateCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderEventBusPort;
import org.labcabrera.sample.archetype.casefolder.application.services.CreateCaseFolderService;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderCreatedEvent;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateCaseFolderCommandHandler implements CommandHandler<CreateCaseFolderCommand, CaseFolder> {

    private final CreateCaseFolderService createCaseFolderService;
    private final CaseFolderEventBusPort caseFolderEventBusPort;

    public CaseFolder handle(CreateCaseFolderCommand command) {
        log.info("Create case folder << {}", command.idCardNumber());
        var caseFolder = createCaseFolderService.createCaseFolder(
            command.name(),
            command.firstSurname(),
            command.lastSurname(),
            command.idCardType(), command.idCardNumber());
        sendNotification(caseFolder);
        return caseFolder;
    }

    private void sendNotification(CaseFolder caseFolder) {
        var event = new CaseFolderCreatedEvent(
            caseFolder.getId(),
            caseFolder.getName(),
            caseFolder.getFirstSurname(),
            caseFolder.getLastSurname(),
            caseFolder.getIdCard().idCardType(),
            caseFolder.getIdCard().idCardNumber(),
            caseFolder.getCreatedAt());
        caseFolderEventBusPort.publish(event);
    }

}