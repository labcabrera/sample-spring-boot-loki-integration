package org.labcabrera.sample.archetype.application.cqrs.queries;

public record GetPlayerByIdQuery(
    String playerId) {
}