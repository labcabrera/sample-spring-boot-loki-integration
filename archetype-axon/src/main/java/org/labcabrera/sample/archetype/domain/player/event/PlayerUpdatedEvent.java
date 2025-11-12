package org.labcabrera.sample.archetype.domain.player.event;

import lombok.Value;

@Value
public class PlayerUpdatedEvent {
    private final String playerId;
    private final String name;
    private final String email;
}
