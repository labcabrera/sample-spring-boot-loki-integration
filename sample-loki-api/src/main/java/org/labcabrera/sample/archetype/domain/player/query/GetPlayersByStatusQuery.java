package org.labcabrera.sample.archetype.domain.player.query;

import lombok.Data;

@Data
public class GetPlayersByStatusQuery {
    private final String status;
}