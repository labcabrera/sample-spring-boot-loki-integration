package org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.entities.CaseHolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseHolderJpaRepository extends
    JpaRepository<CaseHolderEntity, String>,
    JpaSpecificationExecutor<CaseHolderEntity> {

    @Query("SELECT c FROM CaseHolderEntity c WHERE c.firstSurname = ?1 AND c.lastSurname = ?2")
    Optional<CaseHolderEntity> findByIdCardNumber(String email);

}