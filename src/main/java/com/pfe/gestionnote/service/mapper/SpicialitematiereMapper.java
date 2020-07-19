package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.SpicialitematiereDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Spicialitematiere} and its DTO {@link SpicialitematiereDTO}.
 */
@Mapper(componentModel = "spring", uses = {MatiereMapper.class, SpecialiteMapper.class})
public interface SpicialitematiereMapper extends EntityMapper<SpicialitematiereDTO, Spicialitematiere> {

    @Mapping(source = "matiere.id", target = "matiereId")
    @Mapping(source = "specialite.id", target = "specialiteId")
    @Mapping(source = "matiere.nom", target = "matiereNom")
    SpicialitematiereDTO toDto(Spicialitematiere spicialitematiere);

    @Mapping(source = "matiereId", target = "matiere")
    @Mapping(source = "specialiteId", target = "specialite")
    Spicialitematiere toEntity(SpicialitematiereDTO spicialitematiereDTO);

    default Spicialitematiere fromId(Long id) {
        if (id == null) {
            return null;
        }
        Spicialitematiere spicialitematiere = new Spicialitematiere();
        spicialitematiere.setId(id);
        return spicialitematiere;
    }
}
