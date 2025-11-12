package org.labcabrera.sample.archetype.application.cqrs.commands;

import lombok.Value;

@Value
public class CreatePlayerCommand {

    private final String name;
    private final String email;
    private final Integer elo;

}
