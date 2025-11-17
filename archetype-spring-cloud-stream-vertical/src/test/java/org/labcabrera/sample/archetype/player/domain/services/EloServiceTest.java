package org.labcabrera.sample.archetype.player.domain.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EloServiceTest {

    @Test
    void winAgainstWeakerOpponent_updatesEloCorrectly() {
        EloService svc = new EloService(32); // fixed K-factor for deterministic results
        int current = 1600;
        int opponent = 1400;
        int next = svc.calculateNewElo(current, opponent, true);
        // expected delta ~= 7.688 -> round to 8
        assertEquals(1608, next);
    }

    @Test
    void lossAgainstStrongerOpponent_updatesEloCorrectly() {
        EloService svc = new EloService(32);
        int current = 1600;
        int opponent = 1700;
        int next = svc.calculateNewElo(current, opponent, false);
        // expected delta ~= -11.52 -> round to -12
        assertEquals(1588, next);
    }

    @Test
    void drawKeepsEloWhenRatingsEqual() {
        EloService svc = new EloService(32);
        int current = 2000;
        int opponent = 2000;
        int next = svc.calculateNewElo(current, opponent, 0.5);
        assertEquals(2000, next);
    }

    @Test
    void configurableKFactorIsApplied() {
        EloService svc = new EloService(10);
        int current = 1500;
        int opponent = 1500;
        int next = svc.calculateNewElo(current, opponent, 1.0);
        // expected delta = 10*(1-0.5)=5
        assertEquals(1505, next);
    }

    @Test
    void invalidInputsThrow() {
        EloService svc = new EloService(32);
        assertThrows(IllegalArgumentException.class, () -> svc.calculateNewElo(-1, 1500, 1.0));
        assertThrows(IllegalArgumentException.class, () -> svc.calculateNewElo(1500, -20, 1.0));
        assertThrows(IllegalArgumentException.class, () -> svc.calculateNewElo(1500, 1500, -0.1));
        assertThrows(IllegalArgumentException.class, () -> svc.calculateNewElo(1500, 1500, 1.1));
    }

}
