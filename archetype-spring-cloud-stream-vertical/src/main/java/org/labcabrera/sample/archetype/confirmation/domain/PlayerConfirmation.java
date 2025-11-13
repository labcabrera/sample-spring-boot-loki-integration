package org.labcabrera.sample.archetype.confirmation.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PlayerConfirmation {

    private String email;

    private String confirmationCode;

    private String confirmationToken;

    private LocalDateTime createdAt;

    private LocalDateTime revokedAt;

    private LocalDateTime expiresAt;

}
