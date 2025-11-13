package org.labcabrera.sample.archetype.player.domain;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
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
    @Min(1)
    private Integer elo;

    @NotNull
    private PlayerStatus status;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}