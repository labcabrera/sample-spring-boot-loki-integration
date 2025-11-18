package org.labcabrera.sample.archetype.casefolder.domain;

import java.time.LocalDateTime;

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
public class CaseFolder {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String firstSurname;

    private String lastSurname;

    @NotNull
    private IdCard idCard;

    @NotNull
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}