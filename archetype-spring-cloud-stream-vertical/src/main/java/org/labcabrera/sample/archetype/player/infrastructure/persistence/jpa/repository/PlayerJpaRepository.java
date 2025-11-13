package org.labcabrera.sample.archetype.player.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import org.labcabrera.sample.archetype.player.infrastructure.persistence.jpa.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, String>, JpaSpecificationExecutor<PlayerEntity> {

    Optional<PlayerEntity> findByEmail(String email);

}