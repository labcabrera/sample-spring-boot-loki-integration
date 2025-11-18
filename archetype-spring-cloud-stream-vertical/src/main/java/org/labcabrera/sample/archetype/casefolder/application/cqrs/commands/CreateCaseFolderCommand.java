package org.labcabrera.sample.archetype.casefolder.application.cqrs.commands;

import org.labcabrera.sample.archetype.casefolder.domain.IdCardType;

public record CreateCaseFolderCommand(
    String name,
    String firstSurname,
    String lastSurname,
    IdCardType idCardType,
    String idCardNumber) {
}
