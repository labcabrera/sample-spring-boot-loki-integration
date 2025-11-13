package org.labcabrera.sample.archetype.confirmation.infrastructure.repositories;

import org.labcabrera.sample.archetype.player.infrastructure.persistence.jpa.entities.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerConfirmationJpaRepository extends JpaRepository<PlayerEntity, String> {

}
