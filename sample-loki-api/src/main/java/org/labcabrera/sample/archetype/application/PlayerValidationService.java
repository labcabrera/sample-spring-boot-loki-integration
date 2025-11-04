package org.labcabrera.sample.archetype.application;

import org.labcabrera.sample.archetype.application.ports.PlayerRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerValidationService {

    private final PlayerRepository playerRepository;

    public boolean isEmailUnique(String email) {
        boolean exists = playerRepository.existsByEmail(email);
        log.debug("Email {} already exists: {}", email, exists);
        return !exists;
    }

    public void validatePlayerCreation(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (!isEmailUnique(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
    }
}