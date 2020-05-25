package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.MatiereDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Matiere} and its DTO {@link MatiereDTO}.
 */
@Mapper(componentModel = "spring", uses = {RegimeMapper.class})
public interface MatiereMapper extends EntityMapper<MatiereDTO, Matiere> {

    @Mapping(source = "regime.id", target = "regimeId")
    MatiereDTO toDto(Matiere matiere);

    @Mapping(source = "regimeId", target = "regime")
    Matiere toEntity(MatiereDTO matiereDTO);

    default Matiere fromId(Long id) {
        if (id == null) {
            return null;
        }
        Matiere matiere = new Matiere();
        matiere.setId(id);
        return matiere;
    }
}
