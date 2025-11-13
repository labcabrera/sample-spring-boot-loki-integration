package org.labcabrera.sample.archetype.confirmation.infrastructure.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_confirmations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerConfirmationEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "confirmation_code", nullable = false, length = 12)
    private String confirmationCode;

    @Column(name = "confirmation_token", nullable = false, length = 100)
    private String confirmationToken;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked_at", nullable = true)
    private LocalDateTime revokedAt;

    @Column(name = "confirmed_at", nullable = true)
    private LocalDateTime confirmedAt;

}
