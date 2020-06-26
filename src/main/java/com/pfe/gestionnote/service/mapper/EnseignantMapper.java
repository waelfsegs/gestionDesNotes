package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.EnseignantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enseignant} and its DTO {@link EnseignantDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartementMapper.class})
public interface EnseignantMapper extends EntityMapper<EnseignantDTO, Enseignant> {

    @Mapping(source = "departement.id", target = "departementId")
    @Mapping(source = "departement.nomDep", target = "departementName")
    EnseignantDTO toDto(Enseignant enseignant);

    @Mapping(source = "departementId", target = "departement")
    Enseignant toEntity(EnseignantDTO enseignantDTO);

    default Enseignant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enseignant enseignant = new Enseignant();
        enseignant.setId(id);
        return enseignant;
    }
}
