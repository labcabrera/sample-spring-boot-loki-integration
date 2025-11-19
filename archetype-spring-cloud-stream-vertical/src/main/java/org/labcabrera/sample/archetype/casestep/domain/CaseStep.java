package org.labcabrera.sample.archetype.casestep.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CaseStep {

    private String id;

    private String caseFolderId;

    private StepType stepType;

    private StepStatus status;

    private String assignedTo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String owner;

}
