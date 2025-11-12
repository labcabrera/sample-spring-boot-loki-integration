package org.labcabrera.sample.archetype.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerJpaRepository extends JpaRepository<PlayerEntity, String> {

    Optional<PlayerEntity> findByEmail(String email);

    List<PlayerEntity> findByStatus(PlayerEntity.PlayerStatus status);

    @Query("SELECT p FROM PlayerEntity p WHERE p.elo >= :minElo AND p.elo <= :maxElo")
    List<PlayerEntity> findByEloRange(@Param("minElo") Integer minElo, @Param("maxElo") Integer maxElo);

    @Query("SELECT p FROM PlayerEntity p WHERE p.name ILIKE %:name%")
    List<PlayerEntity> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT COUNT(p) FROM PlayerEntity p WHERE p.status = :status")
    Long countByStatus(@Param("status") PlayerEntity.PlayerStatus status);

    // Page<PlayerEntity> findByRsql(String rsql, Pageable pageable);

    boolean existsByEmail(String email);
}