package org.labcabrera.sample.archetype.casestep.interfaces.http.dto;

import java.time.LocalDateTime;

import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casestep.domain.StepStatus;
import org.labcabrera.sample.archetype.casestep.domain.StepType;

public record CaseStepDto(

    String id,

    CaseFolder caseFolder,

    StepType stepType,

    StepStatus status,

    String assignedTo,

    String owner) {

}
