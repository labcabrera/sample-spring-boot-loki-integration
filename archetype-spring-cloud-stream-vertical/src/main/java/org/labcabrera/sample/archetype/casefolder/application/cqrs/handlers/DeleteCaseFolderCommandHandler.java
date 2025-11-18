package org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.DeleteCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderEventBusPort;
import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderRepository;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.domain.events.CaseFolderDeletedEvent;
import org.labcabrera.sample.archetype.shared.application.CommandHandler;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteCaseFolderCommandHandler implements CommandHandler<DeleteCaseFolderCommand, Void> {

    private final CaseFolderRepository playerRepository;
    private final CaseFolderEventBusPort playerEventBusPort;

    @Override
    public Void handle(DeleteCaseFolderCommand command) {
        var caseFolder = playerRepository.findById(command.caseFolderId())
            .orElseThrow(() -> new NotFoundException(command.caseFolderId(), CaseFolder.class));
        playerRepository.deleteById(command.caseFolderId());
        sendNotification(caseFolder);
        return null;
    }

    private void sendNotification(CaseFolder caseFolder) {
        var event = new CaseFolderDeletedEvent(
            caseFolder.getId(),
            caseFolder.getIdCard().idCardType(),
            caseFolder.getIdCard().idCardNumber());
        playerEventBusPort.publish(event);
    }
}
