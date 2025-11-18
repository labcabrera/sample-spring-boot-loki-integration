package org.labcabrera.sample.archetype.player.application.cqrs.queries;

public record GetPlayerByIdQuery(
    String playerId) {
}