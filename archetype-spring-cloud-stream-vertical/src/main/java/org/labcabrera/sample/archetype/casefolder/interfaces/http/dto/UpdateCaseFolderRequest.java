package org.labcabrera.sample.archetype.casefolder.interfaces.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data to update an existing case folder")
public record UpdateCaseFolderRequest(

    @Schema(description = "Name", example = "John", required = false) String name,

    @Schema(description = "First surname", example = "Doe", required = false) String firstSurname,

    @Schema(description = "Last surname", example = "Smith", required = false) String lastSurname

) {
}
