package org.labcabrera.sample.archetype.domain.player.event;

import org.labcabrera.sample.archetype.domain.player.PlayerStatus;

public record PlayerUpdatedEvent(
    String playerId,
    String name,
    String email,
    PlayerStatus status) {
}
