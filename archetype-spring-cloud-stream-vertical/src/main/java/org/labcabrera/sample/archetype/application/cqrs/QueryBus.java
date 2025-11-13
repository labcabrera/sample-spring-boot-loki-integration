package org.labcabrera.sample.archetype.application.cqrs;

public interface QueryBus {

    <R> R dispatch(Object query);

}
