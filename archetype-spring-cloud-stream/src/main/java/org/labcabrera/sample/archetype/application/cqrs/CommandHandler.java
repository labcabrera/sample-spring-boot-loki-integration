package org.labcabrera.sample.archetype.application.cqrs;

public interface CommandHandler<C, R> {

    R handle(C command);

}
