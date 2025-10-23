package org.labcabrera.sample.loki.domain.player.event;

import lombok.Value;

@Value
public class PlayerCreatedEvent {
    private final String playerId;
    private final String name;
}
