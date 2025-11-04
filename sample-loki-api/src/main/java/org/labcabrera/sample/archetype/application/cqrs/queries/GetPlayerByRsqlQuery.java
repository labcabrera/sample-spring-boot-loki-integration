package org.labcabrera.sample.archetype.application.cqrs.queries;

import lombok.Data;

@Data
public class GetPlayerByRsqlQuery {
    private final String rsql;
    private final Integer page;
    private final Integer size;
}
