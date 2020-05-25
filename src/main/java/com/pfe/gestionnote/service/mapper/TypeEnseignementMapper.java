package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.TypeEnseignementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TypeEnseignement} and its DTO {@link TypeEnseignementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TypeEnseignementMapper extends EntityMapper<TypeEnseignementDTO, TypeEnseignement> {



    default TypeEnseignement fromId(Long id) {
        if (id == null) {
            return null;
        }
        TypeEnseignement typeEnseignement = new TypeEnseignement();
        typeEnseignement.setId(id);
        return typeEnseignement;
    }
}
