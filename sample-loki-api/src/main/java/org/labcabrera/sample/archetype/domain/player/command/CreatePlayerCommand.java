package org.labcabrera.sample.archetype.domain.player.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Value;

@Value
public class CreatePlayerCommand {
    @TargetAggregateIdentifier
    private final String playerId;
    private final String name;
    private final String email;
    private final Integer elo;
}
