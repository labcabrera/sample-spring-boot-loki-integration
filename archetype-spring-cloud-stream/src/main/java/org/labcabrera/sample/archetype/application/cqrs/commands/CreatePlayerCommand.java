package org.labcabrera.sample.archetype.application.cqrs.commands;

public record CreatePlayerCommand(
    String name,
    String email,
    Integer elo) {
}
