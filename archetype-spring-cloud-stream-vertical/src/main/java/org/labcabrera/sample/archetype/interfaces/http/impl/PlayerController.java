package org.labcabrera.sample.archetype.interfaces.http.impl;

import org.labcabrera.sample.archetype.domain.player.Player;
import org.labcabrera.sample.archetype.interfaces.http.PlayerControllerDefinition;
import org.labcabrera.sample.archetype.interfaces.http.dto.CreatePlayerRequest;
import org.labcabrera.sample.archetype.interfaces.http.dto.PageResponse;
import org.labcabrera.sample.archetype.interfaces.http.dto.PlayerDto;
import org.labcabrera.sample.archetype.interfaces.http.dto.UpdatePlayerRequest;
import org.labcabrera.sample.archetype.player.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.commands.UpdatePlayerCommand;
import org.labcabrera.sample.archetype.player.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.player.application.cqrs.queries.GetPlayersByRsqlQuery;
import org.labcabrera.sample.archetype.shared.application.CommandBus;
import org.labcabrera.sample.archetype.shared.application.QueryBus;
import org.springframework.data.domain.Page;
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

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<PlayerDto> create(@RequestBody CreatePlayerRequest request) {
        var command = new CreatePlayerCommand(request.name(), request.email(), request.elo());
        Player player = commandBus.dispatch(command);
        var playerDto = objectMapper.convertValue(player, PlayerDto.class);
        return ResponseEntity.ok(playerDto);
    }

    @Override
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable String playerId) {
        var query = new GetPlayerByIdQuery(playerId);
        Player player = queryBus.dispatch(query);
        var playerDto = objectMapper.convertValue(player, PlayerDto.class);
        return ResponseEntity.ok(playerDto);
    }

    @Override
    public ResponseEntity<PageResponse<PlayerDto>> getPlayersByRsql(String rsql, Pageable pageable) {
        var query = new GetPlayersByRsqlQuery(rsql, pageable);
        Page<Player> page = queryBus.dispatch(query);
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
        Player player = commandBus.dispatch(command);
        var playerDto = objectMapper.convertValue(player, PlayerDto.class);
        return ResponseEntity.ok(playerDto);
    }

}
