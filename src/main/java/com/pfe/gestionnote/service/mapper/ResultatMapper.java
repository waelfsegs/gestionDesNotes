package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.ResultatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Resultat} and its DTO {@link ResultatDTO}.
 */
@Mapper(componentModel = "spring", uses = {MatiereMapper.class, InscriptionMapper.class})
public interface ResultatMapper extends EntityMapper<ResultatDTO, Resultat> {

    @Mapping(source = "matiere.id", target = "matiereId")
    @Mapping(source = "inscription.id", target = "inscriptionId")
    ResultatDTO toDto(Resultat resultat);

    @Mapping(source = "matiereId", target = "matiere")
    @Mapping(source = "inscriptionId", target = "inscription")
    Resultat toEntity(ResultatDTO resultatDTO);

    default Resultat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resultat resultat = new Resultat();
        resultat.setId(id);
        return resultat;
    }
}
