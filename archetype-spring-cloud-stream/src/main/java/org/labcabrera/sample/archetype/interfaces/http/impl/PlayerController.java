package org.labcabrera.sample.archetype.interfaces.http.impl;

import java.util.List;
import java.util.Map;

import org.labcabrera.sample.archetype.application.cqrs.commands.CreatePlayerCommand;
import org.labcabrera.sample.archetype.application.cqrs.handlers.CreatePlayerCommandHandler;
import org.labcabrera.sample.archetype.application.cqrs.handlers.GetPlayerByIdQueryHandler;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.application.cqrs.queries.GetPlayersByEloRangeQuery;
import org.labcabrera.sample.archetype.application.services.PlayerService;
import org.labcabrera.sample.archetype.interfaces.http.PlayerControllerDefinition;
import org.labcabrera.sample.archetype.interfaces.http.dto.PlayerDto;
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

    private final PlayerService playerService;
    private final GetPlayerByIdQueryHandler playerQueryHandler;
    private final CreatePlayerCommandHandler createPlayerCommandHandler;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<PlayerDto> create(@RequestBody CreatePlayerRequest request) {
        var command = new CreatePlayerCommand(request.name(), request.email(), request.elo());
        var player = createPlayerCommandHandler.handle(command);
        var playerDto = objectMapper.convertValue(player, PlayerDto.class);
        return ResponseEntity.ok(playerDto);
    }

    @Override
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable String playerId) {
        var query = new GetPlayerByIdQuery(playerId);
        //PlayerView player = queryGateway.query(query, PlayerView.class);
        //return ResponseEntity.ok(player);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ResponseEntity<Map<String, PlayerDto>> getPlayersByRsql(String rsql, Integer page, Integer size) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ResponseEntity<List<PlayerDto>> getPlayersByEloRange(Integer minElo, Integer maxElo) {
        throw new UnsupportedOperationException("Not implemented yet");
        // var query = new GetPlayersByEloRangeQuery(minElo, maxElo);
        // List<PlayerView> players = (List<PlayerView>) queryGateway.query(query, List.class);
        // return ResponseEntity.ok(players);
    }

}
