package org.labcabrera.sample.archetype.casefolder.interfaces.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data to create a new case folder")
public record CreateCaseFolderRequest(

    @Schema(description = "Name", example = "John", required = true) String name,

    @Schema(description = "First surname", example = "Doe", required = true) String firstSurname,

    @Schema(description = "Last surname", example = "Smith", required = false) String lastSurname,

    @Schema(description = "IdCard", required = true) IdCardDto idCard) {
}
