package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.EnseignementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enseignement} and its DTO {@link EnseignementDTO}.
 */
@Mapper(componentModel = "spring", uses = {MatiereMapper.class, EnseignantMapper.class, GroupeMapper.class, TypeEnseignementMapper.class})
public interface EnseignementMapper extends EntityMapper<EnseignementDTO, Enseignement> {

    @Mapping(source = "matiere.id", target = "matiereId")
    @Mapping(source = "enseignant.id", target = "enseignantId")
    @Mapping(source = "groupe.id", target = "groupeId")
    @Mapping(source = "typeEnseignement.id", target = "typeEnseignementId")
    EnseignementDTO toDto(Enseignement enseignement);

    @Mapping(source = "matiereId", target = "matiere")
    @Mapping(source = "enseignantId", target = "enseignant")
    @Mapping(source = "groupeId", target = "groupe")
    @Mapping(source = "typeEnseignementId", target = "typeEnseignement")
    Enseignement toEntity(EnseignementDTO enseignementDTO);

    default Enseignement fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enseignement enseignement = new Enseignement();
        enseignement.setId(id);
        return enseignement;
    }
}
