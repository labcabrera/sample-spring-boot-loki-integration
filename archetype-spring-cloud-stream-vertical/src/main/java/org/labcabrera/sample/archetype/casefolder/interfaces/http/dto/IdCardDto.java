package org.labcabrera.sample.archetype.casefolder.interfaces.http.dto;

import org.labcabrera.sample.archetype.casefolder.domain.IdCardType;

public record IdCardDto(

    IdCardType type,

    String number

) {

}
