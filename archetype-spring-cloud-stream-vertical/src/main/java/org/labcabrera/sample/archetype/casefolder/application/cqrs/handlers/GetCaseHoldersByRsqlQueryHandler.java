package org.labcabrera.sample.archetype.casefolder.application.cqrs.handlers;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.queries.GetCaseFoldersByRsqlQuery;
import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderRepository;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.shared.application.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetCaseHoldersByRsqlQueryHandler implements QueryHandler<GetCaseFoldersByRsqlQuery, Page<CaseFolder>> {

    private final CaseFolderRepository caseFolderRepository;

    public Page<CaseFolder> handle(GetCaseFoldersByRsqlQuery query) {
        log.debug("Handling RSQL query for player <<< {}", query.rsql());
        return caseFolderRepository.findByRsql(query.rsql(), query.pageable());
    }
}
