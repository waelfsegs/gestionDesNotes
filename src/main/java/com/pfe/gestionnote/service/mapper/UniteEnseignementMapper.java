package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.UniteEnseignementDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UniteEnseignement} and its DTO {@link UniteEnseignementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UniteEnseignementMapper extends EntityMapper<UniteEnseignementDTO, UniteEnseignement> {



    default UniteEnseignement fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniteEnseignement uniteEnseignement = new UniteEnseignement();
        uniteEnseignement.setId(id);
        return uniteEnseignement;
    }
}
