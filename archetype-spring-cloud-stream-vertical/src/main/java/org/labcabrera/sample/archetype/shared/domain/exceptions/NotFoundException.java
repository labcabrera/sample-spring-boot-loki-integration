package org.labcabrera.sample.archetype.shared.domain.exceptions;

public class NotFoundException extends DomainException {

    private static final String CODE = "NOT_FOUND";

    public NotFoundException(String code, Class<?> clazz) {
        super(code, 404, clazz.getSimpleName());
    }

    public NotFoundException(String message) {
        super(CODE, 404, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(CODE, 404, message, cause);
    }

}
