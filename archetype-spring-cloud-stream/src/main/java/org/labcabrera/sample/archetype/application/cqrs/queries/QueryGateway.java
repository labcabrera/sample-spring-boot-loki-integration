package org.labcabrera.sample.archetype.application.cqrs.queries;

public interface QueryGateway {

    <T> T query(Object query, Class<T> clazz);

}
