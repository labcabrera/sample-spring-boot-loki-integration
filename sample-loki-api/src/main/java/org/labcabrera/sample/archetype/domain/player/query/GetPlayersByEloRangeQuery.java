package org.labcabrera.sample.archetype.domain.player.query;

import lombok.Data;

@Data
public class GetPlayersByEloRangeQuery {
    private final Integer minElo;
    private final Integer maxElo;
}