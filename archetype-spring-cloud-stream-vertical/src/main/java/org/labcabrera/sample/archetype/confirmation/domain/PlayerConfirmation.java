package org.labcabrera.sample.archetype.confirmation.domain;

import java.time.LocalDateTime;

public record PlayerConfirmation(

    String id,

    String email,

    String confirmationCode,

    String confirmationToken,

    LocalDateTime createdAt,

    LocalDateTime expiresAt,

    LocalDateTime revokedAt,

    LocalDateTime confirmedAt

) {

}
