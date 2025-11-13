package org.labcabrera.sample.archetype.shared.domain.exceptions;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.labcabrera.sample.archetype.player.domain.Player;

import jakarta.validation.ConstraintViolation;
import lombok.Getter;

@Getter
public class ConstraintViolationException extends DomainException {

    private static final String CODE = "VALIDATION_ERROR";

    private Map<String, String> messages;

    public ConstraintViolationException(String message, Set<ConstraintViolation<Player>> violations) {
        super(CODE, 400, message);
        this.messages = violations.stream()
            .map(e -> Map.entry(e.getPropertyPath().toString(), e.getMessage()))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
