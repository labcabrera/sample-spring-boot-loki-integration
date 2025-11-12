package org.labcabrera.sample.archetype.application.ports;

import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository {

    Optional<PlayerEntity> findById(String playerId);

    Optional<PlayerEntity> findByEmail(String email);

    List<PlayerEntity> findByStatus(PlayerEntity.PlayerStatus status);

    List<PlayerEntity> findByEloRange(@Param("minElo") Integer minElo, @Param("maxElo") Integer maxElo);

    List<PlayerEntity> findByNameContainingIgnoreCase(@Param("name") String name);

    Long countByStatus(@Param("status") PlayerEntity.PlayerStatus status);

    //Page<PlayerEntity> findByRsql(String rsql, Pageable pageable);

    boolean existsByEmail(String email);

    PlayerEntity save(PlayerEntity entity);

    List<PlayerEntity> findAll();
}