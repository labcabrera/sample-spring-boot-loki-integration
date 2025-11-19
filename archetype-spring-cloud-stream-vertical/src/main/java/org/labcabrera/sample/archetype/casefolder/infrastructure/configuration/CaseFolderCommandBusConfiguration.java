package org.labcabrera.sample.archetype.casefolder.infrastructure.configuration;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.CreateCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.DeleteCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.UpdateCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers.CreateCaseFolderCommandHandler;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers.DeleteCaseFolderCommandHandler;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers.UpdateCaseFolderCommandHandler;
import org.labcabrera.sample.archetype.shared.application.SimpleCommandBus;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CaseFolderCommandBusConfiguration {

    private final SimpleCommandBus commandBus;
    private final CreateCaseFolderCommandHandler createHandler;
    private final UpdateCaseFolderCommandHandler updateHandler;
    private final DeleteCaseFolderCommandHandler deleteHandler;

    @PostConstruct
    public void registerHandlers() {
        commandBus.registerHandler(CreateCaseFolderCommand.class, createHandler);
        commandBus.registerHandler(UpdateCaseFolderCommand.class, updateHandler);
        commandBus.registerHandler(DeleteCaseFolderCommand.class, deleteHandler);
    }

}
