package org.labcabrera.sample.archetype.player.interfaces.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.kafka.common.requests.ApiError;
import org.labcabrera.sample.archetype.player.interfaces.http.dto.CreatePlayerRequest;
import org.labcabrera.sample.archetype.player.interfaces.http.dto.PlayerDto;
import org.labcabrera.sample.archetype.player.interfaces.http.dto.UpdatePlayerRequest;
import org.labcabrera.sample.archetype.shared.interfaces.http.PageResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/v1/players")
@Tag(name = "Players", description = "API for player management using a CQRS architecture")
public interface PlayerControllerDefinition {

    @GetMapping("/{playerId}")
    @Operation(summary = "Get player by id", description = "Retrieve a specific player by its unique identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player found", content = @Content(schema = @Schema(implementation = PlayerDto.class))),
        @ApiResponse(responseCode = "404", description = "Player not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<PlayerDto> getPlayerById(
        @Parameter(description = "Unique player ID", required = true) @PathVariable String playerId);

    @GetMapping
    @Operation(summary = "Get players by RSQL", description = "Filter players using an RSQL expression with optional pagination")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Players list retrieved successfully", content = @Content(schema = @Schema(implementation = PageResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid RSQL expression", content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    ResponseEntity<PageResponse<PlayerDto>> getPlayersByRsql(
        @Parameter(description = "RSQL expression to filter players", name = "q", required = false) @RequestParam(required = false, name = "q") String rsql,
        @ParameterObject Pageable pageable);

    @PostMapping
    @Operation(summary = "Create new player", description = "Creates a new player using the CQRS pattern. Sends a command that emits an event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Player created successfully", content = @Content(schema = @Schema(implementation = PlayerDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<PlayerDto> create(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Player data to create", required = true, content = @Content(schema = @Schema(implementation = CreatePlayerRequest.class))) @RequestBody CreatePlayerRequest request);

    @PatchMapping("/{playerId}")
    @Operation(summary = "Update player", description = "Updates an existing player using the CQRS pattern. Sends a command that emits an event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Player updated successfully", content = @Content(schema = @Schema(implementation = PlayerDto.class))),
        @ApiResponse(responseCode = "404", description = "Player not found", content = @Content(schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<PlayerDto> update(
        @Parameter(description = "Unique player ID", required = true) @PathVariable String playerId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Player data to update", required = true) @RequestBody UpdatePlayerRequest request);

    @DeleteMapping("/{playerId}")
    @Operation(summary = "Delete player", description = "Deletes an existing player using the CQRS pattern. Sends a command that emits an event.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Player deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Player not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> delete(
        @Parameter(description = "Unique player ID", required = true) @PathVariable String playerId);

    @PostMapping("/{playerId}/resend-confirmation")
    @Operation(summary = "Resend player confirmation", description = "Resends the confirmation for a specific player")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Confirmation resent successfully"),
        @ApiResponse(responseCode = "404", description = "Player not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    ResponseEntity<Void> resendConfirmation(
        @Parameter(description = "Unique player ID", required = true) @PathVariable String playerId);
}
