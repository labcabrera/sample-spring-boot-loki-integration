package org.labcabrera.sample.archetype.casestep.infrastructure.persistence.jpa.repositories;

import org.labcabrera.sample.archetype.casestep.infrastructure.persistence.jpa.entities.CaseStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseStepJpaRepository extends JpaRepository<CaseStepEntity, String> {

}
