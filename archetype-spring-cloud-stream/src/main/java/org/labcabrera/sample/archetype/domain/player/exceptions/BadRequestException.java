package org.labcabrera.sample.archetype.domain.player.exceptions;

public class BadRequestException extends DomainException {

    private static final String CODE = "400";

    public BadRequestException(String message) {
        super(CODE, message);
    }

}
