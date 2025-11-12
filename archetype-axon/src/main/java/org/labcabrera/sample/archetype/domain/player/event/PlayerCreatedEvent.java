package org.labcabrera.sample.archetype.domain.player.event;

import lombok.Value;

@Value
public class PlayerCreatedEvent {
    private final String playerId;
    private final String name;
    private final String email;
    private final Integer elo;
}
