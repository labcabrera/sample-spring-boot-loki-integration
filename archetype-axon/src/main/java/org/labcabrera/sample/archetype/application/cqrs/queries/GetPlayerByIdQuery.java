package org.labcabrera.sample.archetype.application.cqrs.queries;

import lombok.Data;

@Data
public class GetPlayerByIdQuery {
    private final String playerId;
}