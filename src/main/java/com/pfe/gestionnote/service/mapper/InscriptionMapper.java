package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.InscriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inscription} and its DTO {@link InscriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {EtudiantMapper.class, ClasseMapper.class, GroupeMapper.class, SemstreMapper.class})
public interface InscriptionMapper extends EntityMapper<InscriptionDTO, Inscription> {

    @Mapping(source = "etudiant.id", target = "etudiantId")
    @Mapping(source = "classe.id", target = "classeId")
    @Mapping(source = "groupe.id", target = "groupeId")
    @Mapping(source = "semstre.id", target = "semstreId")
    InscriptionDTO toDto(Inscription inscription);

    @Mapping(source = "etudiantId", target = "etudiant")
    @Mapping(source = "classeId", target = "classe")
    @Mapping(source = "groupeId", target = "groupe")
    @Mapping(source = "semstreId", target = "semstre")
    Inscription toEntity(InscriptionDTO inscriptionDTO);

    default Inscription fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inscription inscription = new Inscription();
        inscription.setId(id);
        return inscription;
    }
}
