package org.labcabrera.sample.archetype.domain.player.exceptions;

import lombok.Getter;

public class DomainException extends RuntimeException {

    @Getter
    private String code;

    public DomainException(String code, String message) {
        super(message);
        this.code = code;
    }

    public DomainException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public DomainException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

}
