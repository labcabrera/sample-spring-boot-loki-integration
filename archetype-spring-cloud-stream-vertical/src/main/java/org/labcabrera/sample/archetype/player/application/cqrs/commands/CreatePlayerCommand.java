package org.labcabrera.sample.archetype.player.application.cqrs.commands;

public record CreatePlayerCommand(
    String name,
    String email,
    Integer elo) {
}
