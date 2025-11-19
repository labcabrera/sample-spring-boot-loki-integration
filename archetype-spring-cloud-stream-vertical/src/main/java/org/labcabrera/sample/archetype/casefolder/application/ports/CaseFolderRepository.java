package org.labcabrera.sample.archetype.casefolder.application.ports;

import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.shared.application.SecurityPort.AuthenticatedUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CaseFolderRepository {

    Optional<CaseFolder> findById(String caseFolder);

    Optional<CaseFolder> findByIdCardNumber(String idCardNumber);

    Page<CaseFolder> findByRsql(String rsql, Pageable pageable, AuthenticatedUser user);

    CaseFolder save(CaseFolder entity);

    CaseFolder update(CaseFolder entity);

    void deleteById(String caseFolderId);

}