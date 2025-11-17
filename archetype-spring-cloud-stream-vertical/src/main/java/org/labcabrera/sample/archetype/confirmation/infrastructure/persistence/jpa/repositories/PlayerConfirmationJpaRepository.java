package org.labcabrera.sample.archetype.confirmation.infrastructure.persistence.jpa.repositories;

import java.time.LocalDateTime;

import org.labcabrera.sample.archetype.confirmation.infrastructure.persistence.jpa.entities.PlayerConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlayerConfirmationJpaRepository extends JpaRepository<PlayerConfirmationEntity, String> {

    @Query("UPDATE PlayerConfirmationEntity pc SET pc.revokedAt = ?2 WHERE pc.email = ?1 AND pc.revokedAt IS NULL AND pc.confirmedAt IS NULL")
    void revokePrevious(String email, LocalDateTime now);

}
