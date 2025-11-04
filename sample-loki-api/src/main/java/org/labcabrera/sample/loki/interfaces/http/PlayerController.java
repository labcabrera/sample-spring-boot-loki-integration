package org.labcabrera.sample.loki.interfaces.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.axonframework.queryhandling.QueryGateway;
import org.labcabrera.sample.loki.application.PlayerService;
import org.labcabrera.sample.loki.application.PlayerQueryHandler;
import org.labcabrera.sample.loki.domain.player.query.GetPlayerQuery;
import org.labcabrera.sample.loki.domain.player.query.GetPlayersByStatusQuery;
import org.labcabrera.sample.loki.domain.player.query.GetPlayersByEloRangeQuery;
import org.labcabrera.sample.loki.domain.player.query.PlayerView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
@Tag(name = "Players", description = "API for player management using a CQRS architecture")
public class PlayerController {

    private final PlayerService playerService;
    private final QueryGateway queryGateway;
    private final PlayerQueryHandler playerQueryHandler;

    @PostMapping
    @Operation(
        summary = "Create new player",
        description = "Creates a new player using the CQRS pattern. Sends a command that emits an event."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public Mono<ResponseEntity<PlayerCreatedResponse>> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Player data to create",
                required = true,
                content = @Content(schema = @Schema(implementation = CreatePlayerRequest.class))
            )
            @RequestBody CreatePlayerRequest request) {
        String id = playerService.createPlayer(request.name(), request.email(), request.elo());
        return Mono.just(ResponseEntity.ok(new PlayerCreatedResponse(id, "Player created successfully")));
    }
    
    @GetMapping("/{playerId}")
    @Operation(
        summary = "Get player by ID",
        description = "Retrieve a specific player by its unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player found"),
        @ApiResponse(responseCode = "404", description = "Player not found")
    })
    public Mono<ResponseEntity<PlayerView>> getPlayer(
            @Parameter(description = "Unique player ID", required = true)
            @PathVariable String playerId) {
        try {
            PlayerView player = queryGateway.query(new GetPlayerQuery(playerId), PlayerView.class).join();
            return Mono.just(ResponseEntity.ok(player));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.notFound().build());
        }
    }
    
    @GetMapping
    @Operation(
        summary = "Get all players",
        description = "Returns a complete list of all registered players"
    )
    @ApiResponse(responseCode = "200", description = "Lista de jugadores obtenida exitosamente")
    public Mono<ResponseEntity<Map<String, PlayerView>>> getAllPlayers() {
        Map<String, PlayerView> players = playerQueryHandler.getAllPlayers();
        return Mono.just(ResponseEntity.ok(players));
    }
    
    @GetMapping("/status/{status}")
    @Operation(
        summary = "Get players by status",
        description = "Filter players by their status (ACTIVE, INACTIVE)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Players list filtered by status"),
        @ApiResponse(responseCode = "400", description = "Invalid status")
    })
    public Mono<ResponseEntity<List<PlayerView>>> getPlayersByStatus(
            @Parameter(description = "Player status (ACTIVE, INACTIVE)", required = true)
            @PathVariable String status) {
        try {
            @SuppressWarnings("unchecked")
            List<PlayerView> players = queryGateway.query(new GetPlayersByStatusQuery(status), List.class).join();
            return Mono.just(ResponseEntity.ok(players));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
    }
    
    @GetMapping("/elo")
    @Operation(
        summary = "Get players by ELO range",
        description = "Filter players within a specific ELO score range"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Players list in the specified ELO range"),
        @ApiResponse(responseCode = "400", description = "Invalid range parameters")
    })
    public Mono<ResponseEntity<List<PlayerView>>> getPlayersByEloRange(
            @Parameter(description = "Minimum ELO", required = true)
            @RequestParam Integer minElo,
            @Parameter(description = "Maximum ELO", required = true)
            @RequestParam Integer maxElo) {
        try {
            @SuppressWarnings("unchecked")
            List<PlayerView> players = queryGateway.query(
                new GetPlayersByEloRangeQuery(minElo, maxElo), List.class).join();
            return Mono.just(ResponseEntity.ok(players));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
    }

    @Schema(description = "Data to create a new player")
    public static record CreatePlayerRequest(
        @Schema(description = "Player name", example = "Magnus Carlsen", required = true)
            String name,
        @Schema(description = "Player unique email", example = "magnus@chess.com", required = true)
            String email,
        @Schema(description = "Player ELO score", example = "2800")
            Integer elo
    ) {}
    
    @Schema(description = "Response after creating a player")
    public static record PlayerCreatedResponse(
        @Schema(description = "Unique ID of the created player")
            String id,
        @Schema(description = "Confirmation message")
            String message
    ) {}
}
