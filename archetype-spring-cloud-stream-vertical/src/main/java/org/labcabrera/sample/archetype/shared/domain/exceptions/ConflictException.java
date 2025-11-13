package org.labcabrera.sample.archetype.shared.domain.exceptions;

public class ConflictException extends DomainException {

    private static final String CODE = "CONFLICT";

    public ConflictException(String message) {
        super(CODE, 409, message);
    }

}
