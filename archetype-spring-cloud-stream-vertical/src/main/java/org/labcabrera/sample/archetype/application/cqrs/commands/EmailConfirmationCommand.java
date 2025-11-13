package org.labcabrera.sample.archetype.application.cqrs.commands;

public record EmailConfirmationCommand(
    String playerId,
    String confirmationCode) {
}
