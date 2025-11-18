package org.labcabrera.sample.archetype.application.cqrs.queries;

import org.springframework.data.domain.Pageable;

public record GetPlayersByRsqlQuery(
    String rsql,
    Pageable pageable) {
}
