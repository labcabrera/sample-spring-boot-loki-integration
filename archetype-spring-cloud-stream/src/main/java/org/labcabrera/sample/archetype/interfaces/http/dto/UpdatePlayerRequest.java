package org.labcabrera.sample.archetype.interfaces.http.dto;

import org.labcabrera.sample.archetype.domain.player.PlayerStatus;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data to update an existing player")
public record UpdatePlayerRequest(

    @Schema(description = "Player name", example = "Magnus Carlsen", required = false) String name,

    @Schema(description = "Player status", example = "ACTIVE", required = false) PlayerStatus status) {
}
