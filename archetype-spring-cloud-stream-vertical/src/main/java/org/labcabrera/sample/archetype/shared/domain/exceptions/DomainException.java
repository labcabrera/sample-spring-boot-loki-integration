package org.labcabrera.sample.archetype.shared.domain.exceptions;

import lombok.Getter;

public class DomainException extends RuntimeException {

    @Getter
    private String code;

    @Getter
    private int status;

    public DomainException(String code, int status, String message) {
        super(message);
        this.code = code;
        this.status = status;
    }

    public DomainException(String code, int status, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.status = status;
    }

    public DomainException(String code, int status, Throwable cause) {
        super(cause);
        this.code = code;
        this.status = status;
    }

}
