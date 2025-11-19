package org.labcabrera.sample.archetype.casestep.infrastructure.persistence.jpa.mappers;

import org.labcabrera.sample.archetype.casestep.domain.CaseStep;
import org.labcabrera.sample.archetype.casestep.infrastructure.persistence.jpa.entities.CaseStepEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CaseStepMapper {

    CaseStep toDomain(CaseStepEntity entity);

    @Mapping(target = "version", ignore = true)
    CaseStepEntity toEntity(CaseStep domain);

}
