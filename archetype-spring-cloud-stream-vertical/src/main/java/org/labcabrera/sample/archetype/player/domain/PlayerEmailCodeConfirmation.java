package org.labcabrera.sample.archetype.player.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PlayerEmailCodeConfirmation {

    private String playerId;

    private String confirmationCode;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

}
