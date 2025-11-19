package org.labcabrera.sample.archetype.casestep.domain;

import java.time.LocalDateTime;

import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CaseStep {

    private String id;

    private CaseFolder caseFolder;

    private StepType stepType;

    private StepStatus status;

    private String assignedTo;

    private String owner;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
