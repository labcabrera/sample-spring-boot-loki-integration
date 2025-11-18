package org.labcabrera.sample.archetype.casefolder.application.services;

import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderRepository;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateCaseFolderService {

    private final CaseFolderRepository caseFolderRepository;
    private final Validator validator;

    public CaseFolder updateCaseFolder(String caseFolderId, String name, String firstSurname, String lastSurname) {
        var caseFolder = caseFolderRepository.findById(caseFolderId)
            .orElseThrow(() -> new NotFoundException(caseFolderId, CaseFolder.class));
        if (name != null) {
            caseFolder.setName(name);
        }
        if (firstSurname != null) {
            caseFolder.setFirstSurname(firstSurname);
        }
        if (lastSurname != null) {
            caseFolder.setLastSurname(lastSurname);
        }
        var violations = validator.validate(caseFolder);
        if (!violations.isEmpty()) {
            throw new BadRequestException("Player entity validation failed: " + violations);
        }
        return caseFolderRepository.update(caseFolder);
    }

}
