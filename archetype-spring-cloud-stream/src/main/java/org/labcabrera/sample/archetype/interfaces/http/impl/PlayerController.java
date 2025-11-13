package org.labcabrera.sample.archetype.interfaces.http.impl;

import org.labcabrera.sample.archetype.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.application.cqrs.commands.UpdatePlayerCommand;
import org.labcabrera.sample.archetype.application.cqrs.handlers.CreatePlayerCommandHandler;
import org.labcabrera.sample.archetype.application.cqrs.handlers.GetPlayerByIdQueryHandler;
import org.labcabrera.sample.archetype.application.cqrs.handlers.GetPlayersByRsqlQueryHandler;
import org.labcabrera.sample.archetype.application.cqrs.handlers.UpdatePlayerCommandHandler;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayersByRsqlQuery;
import org.labcabrera.sample.archetype.interfaces.http.PlayerControllerDefinition;
import org.labcabrera.sample.archetype.interfaces.http.dto.CreatePlayerRequest;
import org.labcabrera.sample.archetype.interfaces.http.dto.PageResponse;
import org.labcabrera.sample.archetype.interfaces.http.dto.PlayerDto;
import org.labcabrera.sample.archetype.interfaces.http.dto.UpdatePlayerRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PlayerController implements PlayerControllerDefinition {

    private final CreatePlayerCommandHandler createPlayerCommandHandler;
    private final GetPlayerByIdQueryHandler getPlayerByIdQueryHandler;
    private final GetPlayersByRsqlQueryHandler getPlayersByRsqlQueryHandler;
    private final UpdatePlayerCommandHandler updatePlayerCommandHandler;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<PlayerDto> create(@RequestBody CreatePlayerRequest request) {
        var command = new CreatePlayerCommand(request.name(), request.email(), request.elo());
        var player = createPlayerCommandHandler.handle(command);
        var playerDto = objectMapper.convertValue(player, PlayerDto.class);
        return ResponseEntity.ok(playerDto);
    }

    @Override
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable String playerId) {
        var query = new GetPlayerByIdQuery(playerId);
        var player = getPlayerByIdQueryHandler.handle(query);
        var playerDto = objectMapper.convertValue(player, PlayerDto.class);
        return ResponseEntity.ok(playerDto);
    }

    @Override
    public ResponseEntity<PageResponse<PlayerDto>> getPlayersByRsql(String rsql, Pageable pageable) {
        var query = new GetPlayersByRsqlQuery(rsql, pageable);
        var page = getPlayersByRsqlQueryHandler.handle(query);
        var pageDto = page.map(player -> objectMapper.convertValue(player, PlayerDto.class));
        var response = new PageResponse<>(pageDto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PlayerDto> update(String playerId, UpdatePlayerRequest request) {
        var command = new UpdatePlayerCommand(
            playerId,
            request.name(),
            request.status());
        var player = updatePlayerCommandHandler.handle(command);
        var playerDto = objectMapper.convertValue(player, PlayerDto.class);
        return ResponseEntity.ok(playerDto);
    }

}
