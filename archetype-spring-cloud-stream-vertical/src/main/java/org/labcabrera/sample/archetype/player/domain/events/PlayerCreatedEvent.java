package org.labcabrera.sample.archetype.player.domain.events;

public record PlayerCreatedEvent(
    String playerId,
    String name,
    String email,
    Integer elo) {
}
