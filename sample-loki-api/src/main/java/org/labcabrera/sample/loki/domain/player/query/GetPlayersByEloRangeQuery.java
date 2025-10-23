package org.labcabrera.sample.loki.domain.player.query;

import lombok.Data;

@Data
public class GetPlayersByEloRangeQuery {
    private final Integer minElo;
    private final Integer maxElo;
}