package org.labcabrera.sample.archetype.confirmation.application.cqrs.commands;

public record PlayerConfirmationCommand(
    String playerId,
    String email) {

}
