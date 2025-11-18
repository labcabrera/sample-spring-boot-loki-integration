package org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.queries.GetCaseFolderByIdQuery;
import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderRepository;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.shared.application.QueryHandler;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotFoundException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCaseFolderByIdQueryHandler implements QueryHandler<GetCaseFolderByIdQuery, CaseFolder> {

    private final CaseFolderRepository caseFolderRepository;

    public CaseFolder handle(GetCaseFolderByIdQuery query) {
        log.debug("Getting case folder by id << {}", query.caseFolderId());
        return caseFolderRepository
            .findById(query.caseFolderId())
            .orElseThrow(() -> new NotFoundException(query.caseFolderId(), CaseFolder.class));
    }

}