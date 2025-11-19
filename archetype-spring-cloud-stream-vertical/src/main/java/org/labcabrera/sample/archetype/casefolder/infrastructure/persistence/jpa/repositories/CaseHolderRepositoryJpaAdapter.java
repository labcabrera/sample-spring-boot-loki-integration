package org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.repositories;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.labcabrera.sample.archetype.casefolder.application.ports.CaseFolderRepository;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.entities.CaseHolderEntity;
import org.labcabrera.sample.archetype.shared.domain.exceptions.BadRequestException;
import org.labcabrera.sample.archetype.shared.domain.exceptions.NotModifiedException;
import org.labcabrera.sample.archetype.shared.infrastructure.persistence.rsql.CustomRsqlVisitor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.mappers.CaseFolderMapper;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("null")
public class CaseHolderRepositoryJpaAdapter implements CaseFolderRepository {

    private final CaseHolderJpaRepository jpaRepository;
    private final CaseFolderMapper mapper;
    private final CaseHolderMerger caseHolderMerger;
    private final RSQLParser rsqlParser;

    @Override
    public Optional<CaseFolder> findById(String caseFolderId) {
        return jpaRepository.findById(caseFolderId)
            .map(entity -> mapper.toDomain(entity));
    }

    @Override
    public Optional<CaseFolder> findByIdCardNumber(String idCardNumber) {
        return jpaRepository.findByIdCardNumber(idCardNumber)
            .map(entity -> mapper.toDomain(entity));
    }

    @Override
    public Page<CaseFolder> findByRsql(String rsql, Pageable pageable) {
        if (StringUtils.isBlank(rsql)) {
            var page = jpaRepository.findAll(pageable);
            return page.map(entity -> mapper.toDomain(entity));
        }
        try {
            Node rootNode = rsqlParser.parse(rsql);
            Specification<CaseHolderEntity> spec = rootNode.accept(new CustomRsqlVisitor<CaseHolderEntity>());
            var page = jpaRepository.findAll(spec, pageable);
            return page.map(entity -> mapper.toDomain(entity));
        }
        catch (Exception ex) {
            log.error("Error parsing RSQL query: {}", rsql, ex);
            throw new BadRequestException("Error parsing RSQL query " + rsql, ex);
        }
    }

    @Override
    @Transactional
    public CaseFolder save(CaseFolder caseFolder) {
        try {
            if (caseFolder.getId() != null && jpaRepository.existsById(caseFolder.getId())) {
                throw new BadRequestException("Case folder already exists with id " + caseFolder.getId());
            }
            var entity = mapper.toEntity(caseFolder);
            var savedEntity = jpaRepository.save(entity);
            return mapper.toDomain(savedEntity);
        }
        catch (DataIntegrityViolationException ex) {
            log.error("Data integrity violation while saving case folder: {}", caseFolder, ex);
            throw new BadRequestException("Data integrity error saving case folder", ex);
        }
    }

    @Override
    @Transactional
    public CaseFolder update(CaseFolder caseFolder) {
        var current = jpaRepository.findById(caseFolder.getId())
            .orElseThrow(() -> new BadRequestException("Case folder not found with id " + caseFolder.getId()));
        boolean modified = caseHolderMerger.mergeChanges(current, caseFolder);
        if (!modified) {
            throw new NotModifiedException("No changes detected for case folder with id " + caseFolder.getId());
        }
        var savedEntity = jpaRepository.save(current);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional
    public void deleteById(String caseFolderId) {
        jpaRepository.deleteById(caseFolderId);
    }

}
