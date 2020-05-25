package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.AffectationChefDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AffectationChef} and its DTO {@link AffectationChefDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartementMapper.class, EnseignantMapper.class})
public interface AffectationChefMapper extends EntityMapper<AffectationChefDTO, AffectationChef> {

    @Mapping(source = "departement.id", target = "departementId")
    @Mapping(source = "enseignant.id", target = "enseignantId")
    AffectationChefDTO toDto(AffectationChef affectationChef);

    @Mapping(source = "departementId", target = "departement")
    @Mapping(source = "enseignantId", target = "enseignant")
    AffectationChef toEntity(AffectationChefDTO affectationChefDTO);

    default AffectationChef fromId(Long id) {
        if (id == null) {
            return null;
        }
        AffectationChef affectationChef = new AffectationChef();
        affectationChef.setId(id);
        return affectationChef;
    }
}
