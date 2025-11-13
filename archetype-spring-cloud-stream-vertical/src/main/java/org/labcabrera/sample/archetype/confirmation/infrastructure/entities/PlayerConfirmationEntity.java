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

    @Column(name = "player_id", nullable = false, length = 36)
    private String playerId;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "confirmed", nullable = false)
    private Boolean confirmed = false;

    @Column(name = "confirmation_token", length = 100)
    private String confirmationToken;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
