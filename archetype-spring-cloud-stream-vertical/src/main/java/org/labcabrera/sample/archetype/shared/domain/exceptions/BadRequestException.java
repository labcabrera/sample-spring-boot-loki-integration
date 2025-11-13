package org.labcabrera.sample.archetype.shared.domain.exceptions;

import java.util.Set;

import jakarta.validation.ConstraintViolation;

public class BadRequestException extends DomainException {

    public BadRequestException(String code) {
        super(code, 400);
    }

    public BadRequestException(String code, Throwable cause) {
        super(code, 400, cause);
    }

    public BadRequestException(String code, Set<ConstraintViolation<?>> violations) {
        super(code, 400);
    }

}
