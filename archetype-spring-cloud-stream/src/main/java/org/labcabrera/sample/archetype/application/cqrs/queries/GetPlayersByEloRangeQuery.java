package org.labcabrera.sample.archetype.application.cqrs.queries;

import lombok.Value;

@Value
public class GetPlayersByEloRangeQuery {

    private final Integer minElo;
    private final Integer maxElo;
}