package org.labcabrera.sample.archetype.interfaces.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.labcabrera.sample.archetype.interfaces.http.dto.CreatePlayerRequest;
import org.labcabrera.sample.archetype.interfaces.http.dto.PageResponse;
import org.labcabrera.sample.archetype.interfaces.http.dto.PlayerDto;
import org.labcabrera.sample.archetype.interfaces.http.impl.PlayerController;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/players")
@Tag(name = "Players", description = "API for player management using a CQRS architecture")
public interface PlayerControllerDefinition {

    @PostMapping
    @Operation(summary = "Create new player", description = "Creates a new player using the CQRS pattern. Sends a command that emits an event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<PlayerDto> create(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Player data to create", required = true, content = @Content(schema = @Schema(implementation = CreatePlayerRequest.class))) @RequestBody CreatePlayerRequest request);

    @GetMapping("/{playerId}")
    @Operation(summary = "Get player by id", description = "Retrieve a specific player by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player found"),
        @ApiResponse(responseCode = "404", description = "Player not found")
    })
    ResponseEntity<PlayerDto> getPlayer(
        @Parameter(description = "Unique player ID", required = true) @PathVariable String playerId);

    @GetMapping
    @Operation(summary = "Get players by RSQL", description = "Filter players using an RSQL expression with optional pagination")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Players list retrieved successfully", content = @Content(schema = @Schema(implementation = PageResponse.class))),
    })
    ResponseEntity<PageResponse<PlayerDto>> getPlayersByRsql(
        @Parameter(description = "RSQL expression to filter players", required = false) @RequestParam(required = false) String rsql,
        @ParameterObject Pageable pageable);

}
