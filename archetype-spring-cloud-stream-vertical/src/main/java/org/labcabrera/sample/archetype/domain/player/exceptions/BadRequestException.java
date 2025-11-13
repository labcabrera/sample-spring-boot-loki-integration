package org.labcabrera.sample.archetype.domain.player.exceptions;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

public class BadRequestException extends DomainException {

    private static final String CODE = "BAD_REQUEST";

    public BadRequestException(String message) {
        super(CODE, 400, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(CODE, 400, message, cause);
    }

    public BadRequestException(String message, Set<ConstraintViolation<?>> violations) {
        super(CODE, 400, message);
    }

}
