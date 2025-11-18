package org.labcabrera.sample.archetype.shared.domain.exceptions;

import java.util.Set;

import org.labcabrera.sample.archetype.player.domain.Player;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

@Getter
public class ConstraintViolationException extends DomainException {

    private Set<ConstraintViolation<Player>> violations;

    public ConstraintViolationException(String code, Set<ConstraintViolation<Player>> violations) {
        super(code, 400);
        this.violations = violations;
    }

}
