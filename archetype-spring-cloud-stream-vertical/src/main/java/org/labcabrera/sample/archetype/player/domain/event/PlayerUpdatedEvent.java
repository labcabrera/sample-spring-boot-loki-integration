package org.labcabrera.sample.archetype.player.domain.event;

import org.labcabrera.sample.archetype.player.domain.PlayerStatus;

public record PlayerUpdatedEvent(
    String playerId,
    String name,
    String email,
    PlayerStatus status) {
}
