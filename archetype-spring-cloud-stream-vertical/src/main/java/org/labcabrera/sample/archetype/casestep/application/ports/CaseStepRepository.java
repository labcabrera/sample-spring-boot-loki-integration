package org.labcabrera.sample.archetype.casestep.application.ports;

import java.util.Optional;

import org.labcabrera.sample.archetype.casestep.domain.CaseStep;

public interface CaseStepRepository {

    Optional<CaseStep> findById(String caseStep);

    CaseStep save(CaseStep entity);

}