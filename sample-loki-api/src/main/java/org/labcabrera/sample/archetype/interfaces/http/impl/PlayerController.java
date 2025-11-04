package org.labcabrera.sample.archetype.interfaces.http.impl;

import java.util.List;
import java.util.Map;

import org.axonframework.queryhandling.QueryGateway;
import org.labcabrera.sample.archetype.application.PlayerQueryHandler;
import org.labcabrera.sample.archetype.application.PlayerService;
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
    public ResponseEntity<Map<String, PlayerView>> getAllPlayers() {
        Map<String, PlayerView> players = playerQueryHandler.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @Override
    public ResponseEntity<List<PlayerView>> getPlayersByEloRange(Integer minElo, Integer maxElo) {
        var query = new GetPlayersByEloRangeQuery(minElo, maxElo);
        List<PlayerView> players = queryGateway.query(query, List.class).join();
        return ResponseEntity.ok(players);
    }

}
