package org.labcabrera.sample.archetype.casestep.infrastructure.persistence.jpa.repositories;

import java.util.List;

import org.labcabrera.sample.archetype.casestep.infrastructure.persistence.jpa.entities.CaseStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseStepJpaRepository extends JpaRepository<CaseStepEntity, String> {

    List<CaseStepEntity> findByCaseFolderId(String caseFolderId);

}
