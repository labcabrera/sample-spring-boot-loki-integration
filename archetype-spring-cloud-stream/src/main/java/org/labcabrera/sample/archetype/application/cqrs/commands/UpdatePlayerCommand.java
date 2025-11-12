package org.labcabrera.sample.archetype.application.cqrs.commands;

public record UpdatePlayerCommand(
    String playerId,
    String name) {
}
