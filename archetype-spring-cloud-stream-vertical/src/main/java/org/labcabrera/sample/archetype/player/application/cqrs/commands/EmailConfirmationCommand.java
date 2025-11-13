package org.labcabrera.sample.archetype.player.application.cqrs.commands;

public record EmailConfirmationCommand(
    String playerId,
    String confirmationCode) {
}
