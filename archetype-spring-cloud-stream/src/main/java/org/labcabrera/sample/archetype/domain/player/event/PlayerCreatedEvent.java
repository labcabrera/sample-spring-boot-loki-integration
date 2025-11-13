package org.labcabrera.sample.archetype.domain.player.event;

public record PlayerCreatedEvent(
    String playerId,
    String name,
    String email,
    Integer elo) {
}
