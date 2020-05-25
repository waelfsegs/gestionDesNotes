package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.SpecialiteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specialite} and its DTO {@link SpecialiteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpecialiteMapper extends EntityMapper<SpecialiteDTO, Specialite> {



    default Specialite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Specialite specialite = new Specialite();
        specialite.setId(id);
        return specialite;
    }
}
