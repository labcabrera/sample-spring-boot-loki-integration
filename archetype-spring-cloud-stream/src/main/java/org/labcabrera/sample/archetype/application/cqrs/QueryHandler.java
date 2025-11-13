package org.labcabrera.sample.archetype.application.cqrs;

public interface QueryHandler<Q, R> {

    R handle(Q query);

}
