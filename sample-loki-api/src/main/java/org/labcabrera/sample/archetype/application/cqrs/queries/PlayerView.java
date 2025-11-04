package org.labcabrera.sample.archetype.application.cqrs.queries;

import lombok.Data;

@Data
public class PlayerView {
    private final String playerId;
    private final String name;
    private final String email;
    private final Integer elo;
    private final String status;
}