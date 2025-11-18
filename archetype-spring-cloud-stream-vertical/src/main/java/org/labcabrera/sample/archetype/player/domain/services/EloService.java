package org.labcabrera.sample.archetype.player.domain.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EloService {

    private final int defaultKFactor;

    public EloService(@Value("${app.elo.k-factor:0}") int defaultKFactor) {
        this.defaultKFactor = defaultKFactor;
    }

    public int calculateNewElo(int currentElo, int opponentElo, double result) {
        if (currentElo < 0 || opponentElo < 0) {
            throw new IllegalArgumentException("Elo values must be non-negative");
        }
        if (result < 0.0 || result > 1.0) {
            throw new IllegalArgumentException("Result must be in range [0.0, 1.0]");
        }
        int kFactor = determineKFactor(currentElo);
        double expectedScore = 1.0 / (1.0 + Math.pow(10.0, (opponentElo - currentElo) / 400.0));
        double delta = kFactor * (result - expectedScore);
        int newElo = currentElo + (int) Math.round(delta);
        log.debug("Calculated new Elo: {} (currentElo: {}, kFactor: {}, delta: {})", newElo, currentElo, kFactor, delta);
        return newElo;
    }

    public int calculateNewElo(int currentElo, int opponentElo, boolean didWin) {
        return calculateNewElo(currentElo, opponentElo, didWin ? 1.0 : 0.0);
    }

    private int determineKFactor(int rating) {
        if (defaultKFactor > 0) {
            return defaultKFactor;
        }
        if (rating < 2100) {
            return 32;
        }
        if (rating < 2400) {
            return 24;
        }
        return 16;
    }

}
