package org.labcabrera.sample.archetype.casefolder.interfaces.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Data to create a new case folder")
public record CreateCaseFolderRequest(

    @Schema(description = "Name", example = "John", required = true) @NotNull String name,

    @Schema(description = "First surname", example = "Doe", required = true) @NotNull String firstSurname,

    @Schema(description = "Last surname", example = "Smith", required = false) String lastSurname,

    @Schema(description = "IdCard", required = true) @NotNull IdCardDto idCard) {
}
