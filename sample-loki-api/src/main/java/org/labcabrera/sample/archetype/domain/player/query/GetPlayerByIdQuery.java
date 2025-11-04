package org.labcabrera.sample.archetype.domain.player.query;

import lombok.Data;

@Data
public class GetPlayerByIdQuery {
    private final String playerId;
}