package org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.repositories;

import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.entities.CaseHolderEntity;
import org.springframework.stereotype.Component;

@Component
public class CaseHolderMerger {

    public boolean mergeChanges(CaseHolderEntity current, CaseFolder updated) {
        boolean modified = false;
        if (!current.getName().equals(updated.getName())) {
            current.setName(updated.getName());
            modified = true;
        }
        if (!current.getFirstSurname().equals(updated.getFirstSurname())) {
            current.setFirstSurname(updated.getFirstSurname());
            modified = true;
        }
        if (current.getLastSurname() != null && !current.getLastSurname().equals(updated.getLastSurname())) {
            current.setLastSurname(updated.getLastSurname());
            modified = true;
        }
        return modified;
    }

}
