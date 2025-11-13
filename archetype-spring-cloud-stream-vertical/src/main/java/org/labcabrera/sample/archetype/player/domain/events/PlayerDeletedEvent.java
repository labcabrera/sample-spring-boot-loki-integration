package org.labcabrera.sample.archetype.player.domain.events;

public record PlayerDeletedEvent(
    String playerId,
    String name,
    String email) {
}
