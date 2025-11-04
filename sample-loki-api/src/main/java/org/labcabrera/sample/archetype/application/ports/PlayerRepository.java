package org.labcabrera.sample.archetype.application.ports;

import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, String> {

    Optional<PlayerEntity> findByEmail(String email);

    List<PlayerEntity> findByStatus(PlayerEntity.PlayerStatus status);

    @Query("SELECT p FROM PlayerEntity p WHERE p.elo >= :minElo AND p.elo <= :maxElo")
    List<PlayerEntity> findByEloRange(@Param("minElo") Integer minElo, @Param("maxElo") Integer maxElo);

    @Query("SELECT p FROM PlayerEntity p WHERE p.name ILIKE %:name%")
    List<PlayerEntity> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT COUNT(p) FROM PlayerEntity p WHERE p.status = :status")
    Long countByStatus(@Param("status") PlayerEntity.PlayerStatus status);

    boolean existsByEmail(String email);
}