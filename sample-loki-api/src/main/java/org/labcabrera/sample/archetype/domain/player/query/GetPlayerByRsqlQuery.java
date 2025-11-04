package org.labcabrera.sample.archetype.domain.player.query;

import lombok.Data;

@Data
public class GetPlayerByRsqlQuery {
    private final String rsql;
    private final Integer page;
    private final Integer size;
}
