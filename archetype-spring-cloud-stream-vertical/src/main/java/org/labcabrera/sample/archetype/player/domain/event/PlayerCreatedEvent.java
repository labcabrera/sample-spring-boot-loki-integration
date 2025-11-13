package org.labcabrera.sample.archetype.player.domain.event;

public record PlayerCreatedEvent(
    String playerId,
    String name,
    String email,
    Integer elo) {
}
