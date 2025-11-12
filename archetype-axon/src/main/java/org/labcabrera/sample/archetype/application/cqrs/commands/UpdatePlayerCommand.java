package org.labcabrera.sample.archetype.application.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Value;

@Value
public class UpdatePlayerCommand {
    @TargetAggregateIdentifier
    private final String playerId;
    private final String name;
    private final String email;
}
