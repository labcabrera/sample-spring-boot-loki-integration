package org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.entities.CaseFolderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseFolderJpaRepository extends
    JpaRepository<CaseFolderEntity, String>,
    JpaSpecificationExecutor<CaseFolderEntity> {

    @Query("SELECT c FROM CaseFolderEntity c WHERE c.firstSurname = ?1 AND c.lastSurname = ?2")
    Optional<CaseFolderEntity> findByIdCardNumber(String email);

}