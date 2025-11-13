package org.labcabrera.sample.archetype.application.cqrs;

public interface CommandBus {

    <R> R dispatch(Object command);

}
