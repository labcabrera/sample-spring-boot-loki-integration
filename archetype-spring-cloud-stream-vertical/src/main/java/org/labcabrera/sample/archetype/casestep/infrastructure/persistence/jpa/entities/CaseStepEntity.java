package org.labcabrera.sample.archetype.casestep.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.entities.CaseFolderEntity;
import org.labcabrera.sample.archetype.casestep.domain.StepStatus;
import org.labcabrera.sample.archetype.casestep.domain.StepType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "case_step")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaseStepEntity {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @ManyToOne(optional = false)
    private CaseFolderEntity caseFolder;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_type", nullable = false, length = 50)
    private StepType stepType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private StepStatus status;

    @Column(name = "assigned_to", length = 100)
    private String assignedTo;

    @Column(name = "owner", nullable = false, length = 100)
    private String owner;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

}
