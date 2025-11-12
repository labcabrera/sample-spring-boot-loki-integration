package org.labcabrera.sample.archetype.application.cqrs.queries;

import lombok.Value;

@Value
public class GetPlayerByIdQuery {

    private final String playerId;

}