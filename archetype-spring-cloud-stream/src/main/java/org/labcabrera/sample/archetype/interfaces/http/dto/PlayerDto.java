package org.labcabrera.sample.archetype.interfaces.http.dto;

import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity.PlayerStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Player information")
public class PlayerDto {

    @Schema(description = "Unique identifier of the player", example = "550e8400-e29b-41d4-a716-446655440000")
    private String id;

    @Schema(description = "Player name", example = "Magnus Carlsen", required = true)
    private String name;

    @Schema(description = "Player email address", example = "magnus@chess.com", required = true)
    private String email;

    @Schema(description = "Player ELO rating", example = "2800", minimum = "0", maximum = "3000")
    private Integer elo;

    @Schema(description = "Current status of the player", example = "ACTIVE")
    private PlayerStatus status;

}
