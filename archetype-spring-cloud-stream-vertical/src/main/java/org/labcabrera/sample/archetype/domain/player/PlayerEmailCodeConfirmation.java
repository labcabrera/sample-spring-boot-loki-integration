package org.labcabrera.sample.archetype.domain.player;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PlayerEmailCodeConfirmation {

    private String playerId;

    private String confirmationCode;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

}
