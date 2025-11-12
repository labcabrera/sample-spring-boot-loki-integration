package org.labcabrera.sample.archetype.domain.player.aggregate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.labcabrera.sample.archetype.infrastructure.persistence.entity.PlayerEntity.PlayerStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class Player {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private Integer elo;

    @NotNull
    private PlayerStatus status;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}