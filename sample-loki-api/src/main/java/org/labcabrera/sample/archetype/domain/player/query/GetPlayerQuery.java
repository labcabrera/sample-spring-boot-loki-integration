package org.labcabrera.sample.archetype.domain.player.query;

import lombok.Data;

@Data
public class GetPlayerQuery {
    private final String playerId;
}