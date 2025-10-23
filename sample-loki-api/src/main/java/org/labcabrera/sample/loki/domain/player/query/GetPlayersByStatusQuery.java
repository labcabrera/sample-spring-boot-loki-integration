package org.labcabrera.sample.loki.domain.player.query;

import lombok.Data;

@Data
public class GetPlayersByStatusQuery {
    private final String status;
}