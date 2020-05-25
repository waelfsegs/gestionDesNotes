package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.EtudiantDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Etudiant} and its DTO {@link EtudiantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EtudiantMapper extends EntityMapper<EtudiantDTO, Etudiant> {



    default Etudiant fromId(Long id) {
        if (id == null) {
            return null;
        }
        Etudiant etudiant = new Etudiant();
        etudiant.setId(id);
        return etudiant;
    }
}
