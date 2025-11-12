package org.labcabrera.sample.archetype.domain.player.exceptions;

public class NotFoundException extends DomainException {

    private static final String CODE = "404";

    public NotFoundException(String id, Class<?> clazz) {
        super(CODE, "Not found " + clazz.getSimpleName() + " with id " + id);
    }

    public NotFoundException(String message) {
        super(CODE, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(CODE, message, cause);
    }

}
