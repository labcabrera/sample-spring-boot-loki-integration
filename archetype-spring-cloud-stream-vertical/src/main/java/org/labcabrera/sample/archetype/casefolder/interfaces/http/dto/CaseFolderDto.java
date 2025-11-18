package org.labcabrera.sample.archetype.casefolder.interfaces.http.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Case folder information")
public record CaseFolderDto(

    @Schema(description = "Unique identifier of the case folder", example = "550e8400-e29b-41d4-a716-446655440000") String id,

    @Schema(description = "Case folder name", example = "John", required = true) String name,

    @Schema(description = "Case folder first surname", example = "Doe", required = true) String firstSurname,

    @Schema(description = "Case folder last surname", example = "Smith", required = false) String lastSurname,

    @Schema(description = "Case folder IdCard", required = true) IdCardDto idCard

) {
}
