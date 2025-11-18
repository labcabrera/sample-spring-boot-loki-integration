package org.labcabrera.sample.archetype.shared.domain.exceptions;

import java.util.Set;

import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

@Getter
public class ConstraintViolationException extends DomainException {

    private Set<ConstraintViolation<CaseFolder>> violations;

    // public ConstraintViolationException(String code, Set<ConstraintViolation<?>> violations) {
    //     super(code, 400);
    //     this.violations = violations;
    // }

    public ConstraintViolationException(String code, Set<ConstraintViolation<CaseFolder>> violations) {
        super(code, 400);
        this.violations = violations;
    }

}
