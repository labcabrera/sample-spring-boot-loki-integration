package org.labcabrera.sample.archetype.application.cqrs.commands;

import lombok.Value;

@Value
public class UpdatePlayerCommand {
    private final String playerId;
    private final String name;
    private final String email;
}
