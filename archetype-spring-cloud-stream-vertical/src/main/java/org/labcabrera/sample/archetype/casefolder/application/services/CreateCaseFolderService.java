package org.labcabrera.sample.archetype.casefolder.application.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderRepository;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.domain.IdCard;
import org.labcabrera.sample.archetype.casefolder.domain.IdCardType;
import org.labcabrera.sample.archetype.shared.domain.exceptions.ConstraintViolationException;
import org.springframework.stereotype.Service;

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
            .name(StringUtils.upperCase(name))
            .firstSurname(StringUtils.upperCase(firstSurname))
            .lastSurname(StringUtils.upperCase(lastSurname))
            .idCard(new IdCard(idCardNumber, idCardType))
            .createdAt(LocalDateTime.now())
            .build();
        var violations = validator.validate(caseFolder);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("case-folder.msg.err.validation-error", violations);
        }
        return caseFolderRepository.save(caseFolder);
    }

}
