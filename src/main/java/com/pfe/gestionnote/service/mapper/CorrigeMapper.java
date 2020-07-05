package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.CorrigeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Corrige} and its DTO {@link CorrigeDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnseignantMapper.class, EnveloppeMapper.class})
public interface CorrigeMapper extends EntityMapper<CorrigeDTO, Corrige> {

    @Mapping(source = "enseignant.id", target = "enseignantId")
    @Mapping(source = "enseignant.nom", target = "enseignantNom")
    @Mapping(source = "enseignant.pernom", target = "enseignantPrenom")
    @Mapping(source = "enveloppe.id", target = "enveloppeId")
    @Mapping(source = "enveloppe.nameenv", target = "enveloppenameenv")
    CorrigeDTO toDto(Corrige corrige);

    @Mapping(source = "enseignantId", target = "enseignant")

    @Mapping(source = "enveloppeId", target = "enveloppe")
    Corrige toEntity(CorrigeDTO corrigeDTO);

    default Corrige fromId(Long id) {
        if (id == null) {
            return null;
        }
        Corrige corrige = new Corrige();
        corrige.setId(id);
        return corrige;
    }
}
