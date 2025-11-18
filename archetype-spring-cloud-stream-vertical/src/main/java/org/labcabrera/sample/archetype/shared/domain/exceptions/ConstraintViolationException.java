package org.labcabrera.sample.archetype.shared.domain.exceptions;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

@Getter
public class ConstraintViolationException extends DomainException {

    private Set<ConstraintViolation<?>> violations;

    public ConstraintViolationException(String code, Set<ConstraintViolation<?>> violations) {
        super(code, 400);
        this.violations = violations;
    }

}
