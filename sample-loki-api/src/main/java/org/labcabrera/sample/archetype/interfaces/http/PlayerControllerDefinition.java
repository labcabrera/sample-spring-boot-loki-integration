package org.labcabrera.sample.archetype.interfaces.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.labcabrera.sample.archetype.interfaces.http.impl.PlayerController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/v1/players")
@Tag(name = "Players", description = "API for player management using a CQRS architecture")
public interface PlayerControllerDefinition {

    @PostMapping
    @Operation(summary = "Create new player", description = "Creates a new player using the CQRS pattern. Sends a command that emits an event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<PlayerController.PlayerCreatedResponse> create(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Player data to create", required = true, content = @Content(schema = @Schema(implementation = PlayerController.CreatePlayerRequest.class))) @RequestBody PlayerController.CreatePlayerRequest request);

    @GetMapping("/{playerId}")
    @Operation(summary = "Get player by ID", description = "Retrieve a specific player by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player found"),
        @ApiResponse(responseCode = "404", description = "Player not found")
    })
    ResponseEntity<org.labcabrera.sample.archetype.domain.player.query.PlayerView> getPlayer(
        @Parameter(description = "Unique player ID", required = true) @PathVariable String playerId);

    @GetMapping
    @Operation(summary = "Get all players", description = "Returns a complete list of all registered players")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Players list retrieved successfully")
    })
    ResponseEntity<Map<String, org.labcabrera.sample.archetype.domain.player.query.PlayerView>> getAllPlayers();

    @GetMapping("/status/{status}")
    @Operation(summary = "Get players by status", description = "Filter players by their status (ACTIVE, INACTIVE)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Players list filtered by status"),
        @ApiResponse(responseCode = "400", description = "Invalid status")
    })
    ResponseEntity<List<org.labcabrera.sample.archetype.domain.player.query.PlayerView>> getPlayersByStatus(
        @Parameter(description = "Player status (ACTIVE, INACTIVE)", required = true) @PathVariable String status);

    @GetMapping("/elo")
    @Operation(summary = "Get players by ELO range", description = "Filter players within a specific ELO score range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Players list in the specified ELO range"),
        @ApiResponse(responseCode = "400", description = "Invalid range parameters")
    })
    ResponseEntity<List<org.labcabrera.sample.archetype.domain.player.query.PlayerView>> getPlayersByEloRange(
        @Parameter(description = "Minimum ELO", required = true) @RequestParam Integer minElo,
        @Parameter(description = "Maximum ELO", required = true) @RequestParam Integer maxElo);

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
