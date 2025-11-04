package org.labcabrera.sample.archetype.interfaces.http.impl;

import java.util.List;
import java.util.Map;

import org.axonframework.queryhandling.QueryGateway;
import org.labcabrera.sample.archetype.application.PlayerService;
import org.labcabrera.sample.archetype.application.cqrs.handlers.PlayerQueryHandler;
import org.labcabrera.sample.archetype.domain.player.query.GetPlayerByIdQuery;
import org.labcabrera.sample.archetype.domain.player.query.GetPlayersByEloRangeQuery;
import org.labcabrera.sample.archetype.domain.player.query.PlayerView;
import org.labcabrera.sample.archetype.interfaces.http.PlayerControllerDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PlayerController implements PlayerControllerDefinition {

    private final PlayerService playerService;
    private final QueryGateway queryGateway;
    private final PlayerQueryHandler playerQueryHandler;

    @Override
    public ResponseEntity<PlayerCreatedResponse> create(@RequestBody CreatePlayerRequest request) {
        String id = playerService.createPlayer(request.name(), request.email(), request.elo());
        return ResponseEntity.ok(new PlayerCreatedResponse(id, "Player created successfully"));
    }

    @Override
    public ResponseEntity<PlayerView> getPlayer(@PathVariable String playerId) {
        var query = new GetPlayerByIdQuery(playerId);
        PlayerView player = queryGateway.query(query, PlayerView.class).join();
        return ResponseEntity.ok(player);
    }

    @Override
    public ResponseEntity<Map<String, PlayerView>> getPlayersByRsql(String rsql, Integer page, Integer size) {
        // Basic implementation: if rsql is provided we currently do not evaluate it, so log and return full result.
        if (rsql != null && !rsql.trim().isEmpty()) {
            log.warn("RSQL filtering is not implemented yet. Received expression: {}", rsql);
        }
        Map<String, PlayerView> allPlayers = playerQueryHandler.getAllPlayers();
        if (page == null) {
            page = 0;
        }
        if (size == null || size <= 0) {
            size = 10;
        }
        var entries = allPlayers.entrySet().stream().toList();
        int fromIndex = page * size;
        if (fromIndex >= entries.size()) {
            return ResponseEntity.ok(Map.of());
        }
        int toIndex = Math.min(fromIndex + size, entries.size());
        var pageEntries = entries.subList(fromIndex, toIndex);

        var result = pageEntries.stream().collect(java.util.stream.Collectors.toMap(
            java.util.Map.Entry::getKey,
            java.util.Map.Entry::getValue));

        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<PlayerView>> getPlayersByEloRange(Integer minElo, Integer maxElo) {
        var query = new GetPlayersByEloRangeQuery(minElo, maxElo);
        List<PlayerView> players = queryGateway.query(query, List.class).join();
        return ResponseEntity.ok(players);
    }

}
