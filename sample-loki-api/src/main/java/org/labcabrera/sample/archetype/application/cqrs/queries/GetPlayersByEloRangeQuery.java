package org.labcabrera.sample.archetype.application.cqrs.queries;

import lombok.Data;

@Data
public class GetPlayersByEloRangeQuery {
    private final Integer minElo;
    private final Integer maxElo;
}