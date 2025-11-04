package org.labcabrera.sample.archetype.interfaces.http;

import java.util.List;
import java.util.Map;

import org.axonframework.queryhandling.QueryGateway;
import org.labcabrera.sample.archetype.application.PlayerQueryHandler;
import org.labcabrera.sample.archetype.application.PlayerService;
import org.labcabrera.sample.archetype.domain.player.query.GetPlayerQuery;
import org.labcabrera.sample.archetype.domain.player.query.GetPlayersByEloRangeQuery;
import org.labcabrera.sample.archetype.domain.player.query.GetPlayersByStatusQuery;
import org.labcabrera.sample.archetype.domain.player.query.PlayerView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
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
        try {
            PlayerView player = queryGateway.query(new GetPlayerQuery(playerId), PlayerView.class).join();
            return ResponseEntity.ok(player);
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Map<String, PlayerView>> getAllPlayers() {
        Map<String, PlayerView> players = playerQueryHandler.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @Override
    public ResponseEntity<List<PlayerView>> getPlayersByStatus(@PathVariable String status) {
        try {
            @SuppressWarnings("unchecked")
            List<PlayerView> players = queryGateway.query(new GetPlayersByStatusQuery(status), List.class).join();
            return ResponseEntity.ok(players);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<List<PlayerView>> getPlayersByEloRange(@RequestParam Integer minElo, @RequestParam Integer maxElo) {
        try {
            @SuppressWarnings("unchecked")
            List<PlayerView> players = queryGateway.query(
                new GetPlayersByEloRangeQuery(minElo, maxElo), List.class).join();
            return ResponseEntity.ok(players);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Schema(description = "Data to create a new player")
    public static record CreatePlayerRequest(@Schema(description = "Player name", example = "Magnus Carlsen", required = true) String name,
        @Schema(description = "Player unique email", example = "magnus@chess.com", required = true) String email,
        @Schema(description = "Player ELO score", example = "2800") Integer elo) {
    }

    @Schema(description = "Response after creating a player")
    public static record PlayerCreatedResponse(@Schema(description = "Unique ID of the created player") String id,
        @Schema(description = "Confirmation message") String message) {
    }

}
