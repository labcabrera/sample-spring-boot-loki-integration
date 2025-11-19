package org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.archetype.casefolder.domain.CaseFolder;
import org.labcabrera.sample.archetype.casefolder.infrastructure.persistence.jpa.entities.CaseHolderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { IdCardMapper.class })
public interface CaseFolderMapper {

    CaseFolder toDomain(CaseHolderEntity entity);

    @Mapping(target = "version", ignore = true)
    CaseHolderEntity toEntity(CaseFolder domain);

}
