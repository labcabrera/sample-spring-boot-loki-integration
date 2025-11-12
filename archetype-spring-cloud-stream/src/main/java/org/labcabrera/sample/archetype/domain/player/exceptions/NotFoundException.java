package org.labcabrera.sample.archetype.domain.player.exceptions;

public class NotFoundException extends DomainException {

    private static final String CODE = "NOT_FOUND";

    public NotFoundException(String id, Class<?> clazz) {
        super(CODE, 404, "Not found " + clazz.getSimpleName() + " with id " + id);
    }

    public NotFoundException(String message) {
        super(CODE, 404, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(CODE, 404, message, cause);
    }

}
