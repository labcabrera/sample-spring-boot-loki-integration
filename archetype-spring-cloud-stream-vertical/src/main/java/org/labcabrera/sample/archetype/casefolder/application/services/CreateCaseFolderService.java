package org.labcabrera.sample.archetype.casefolder.application.services;

import java.util.Set;
import java.util.UUID;

import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderRepository;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.domain.IdCard;
import org.labcabrera.sample.archetype.casefolder.domain.IdCardType;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCaseFolderService {

    private final CaseFolderRepository caseFolderRepository;
    private final Validator validator;

    public CaseFolder createCaseFolder(String name, String firstSurname, String lastSurname, IdCardType idCardType, String idCardNumber) {
        var caseFolder = CaseFolder.builder()
            .id(UUID.randomUUID().toString())
            .name(name)
            .firstSurname(firstSurname)
            .lastSurname(lastSurname)
            .idCard(new IdCard(idCardNumber, idCardType))
            .build();
        Set<ConstraintViolation<CaseFolder>> violations = validator.validate(caseFolder);
        if (!violations.isEmpty()) {
            //throw new ConstraintViolationException("player.msg.err.validation-error", violations);
        }
        return caseFolderRepository.save(caseFolder);
    }

}
