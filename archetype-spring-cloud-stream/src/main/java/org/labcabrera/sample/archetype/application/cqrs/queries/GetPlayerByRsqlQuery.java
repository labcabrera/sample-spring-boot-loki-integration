package org.labcabrera.sample.archetype.application.cqrs.queries;

import org.springframework.data.domain.Pageable;

import lombok.Value;

@Value
public class GetPlayerByRsqlQuery {

    private final String rsql;
    private Pageable pageable;
}
