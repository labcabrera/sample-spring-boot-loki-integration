package org.labcabrera.sample.archetype.confirmation.infrastructure.repositories;

import org.labcabrera.sample.archetype.confirmation.infrastructure.entities.PlayerConfirmationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerConfirmationJpaRepository extends JpaRepository<PlayerConfirmationEntity, String> {

}
