package org.labcabrera.sample.archetype.domain.player.event;

public record PlayerUpdatedEvent(
    String playerId,
    String name,
    String email) {
}
