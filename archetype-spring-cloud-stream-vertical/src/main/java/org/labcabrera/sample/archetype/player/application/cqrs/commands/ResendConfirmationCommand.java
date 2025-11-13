package org.labcabrera.sample.archetype.player.application.cqrs.commands;

public record ResendConfirmationCommand(
    String playerId) {
}
