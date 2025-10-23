package org.labcabrera.sample.loki.domain.player.query;

import lombok.Data;

@Data
public class PlayerView {
    private final String playerId;
    private final String name;
    private final String email;
    private final Integer elo;
    private final String status;
}