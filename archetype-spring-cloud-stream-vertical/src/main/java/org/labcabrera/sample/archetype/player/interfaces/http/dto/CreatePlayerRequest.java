package org.labcabrera.sample.archetype.player.interfaces.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data to create a new player")
public record CreatePlayerRequest(

    @Schema(description = "Player name", example = "Magnus Carlsen", required = true) String name,

    @Schema(description = "Player unique email", example = "magnus@chess.com", required = true) String email,

    @Schema(description = "Player ELO score", example = "2800") Integer elo) {
}
