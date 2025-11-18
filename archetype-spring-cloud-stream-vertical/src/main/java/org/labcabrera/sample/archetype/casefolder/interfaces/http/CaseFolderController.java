package org.labcabrera.sample.archetype.casefolder.interfaces.http;

import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.CreateCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.DeleteCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.commands.UpdateCaseFolderCommand;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.queries.GetCaseFolderByIdQuery;
import org.labcabrera.sample.archetype.casefolder.application.cqrs.queries.GetCaseFoldersByRsqlQuery;
import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.interfaces.http.dto.CreateCaseFolderRequest;
import org.labcabrera.sample.archetype.casefolder.interfaces.http.dto.CaseFolderDto;
import org.labcabrera.sample.archetype.casefolder.interfaces.http.dto.UpdateCaseFolderRequest;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.labcabrera.sample.archetype.shared.application.QueryBus;
import org.labcabrera.sample.archetype.shared.interfaces.http.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CaseFolderController implements CaseFolderControllerDefinition {

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<CaseFolderDto> create(@RequestBody @Valid @Validated CreateCaseFolderRequest request) {
        var command = new CreateCaseFolderCommand(
            request.name(),
            request.firstSurname(),
            request.lastSurname(),
            request.idCard().type(),
            request.idCard().number());
        CaseFolder caseFolder = commandBus.dispatch(command);
        var playerDto = objectMapper.convertValue(caseFolder, CaseFolderDto.class);
        return ResponseEntity.status(201).body(playerDto);
    }

    @Override
    public ResponseEntity<CaseFolderDto> getCaseFolderById(@PathVariable String playerId) {
        var query = new GetCaseFolderByIdQuery(playerId);
        CaseFolder player = queryBus.dispatch(query);
        var playerDto = objectMapper.convertValue(player, CaseFolderDto.class);
        return ResponseEntity.ok(playerDto);
    }

    @Override
    public ResponseEntity<PageResponse<CaseFolderDto>> getCaseFoldersByRsql(String rsql, Pageable pageable) {
        var query = new GetCaseFoldersByRsqlQuery(rsql, pageable);
        Page<CaseFolder> page = queryBus.dispatch(query);
        var pageDto = page.map(player -> objectMapper.convertValue(player, CaseFolderDto.class));
        var response = new PageResponse<>(pageDto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<CaseFolderDto> update(String playerId, UpdateCaseFolderRequest request) {
        var command = new UpdateCaseFolderCommand(
            playerId,
            request.name(),
            request.firstSurname(),
            request.lastSurname());
        CaseFolder player = commandBus.dispatch(command);
        var playerDto = objectMapper.convertValue(player, CaseFolderDto.class);
        return ResponseEntity.ok(playerDto);
    }

    @Override
    public ResponseEntity<Void> delete(String playerId) {
        var command = new DeleteCaseFolderCommand(playerId);
        commandBus.dispatch(command);
        return ResponseEntity.noContent().build();
    }

}
