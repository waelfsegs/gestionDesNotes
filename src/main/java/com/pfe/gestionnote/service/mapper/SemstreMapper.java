package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.SemstreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Semstre} and its DTO {@link SemstreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SemstreMapper extends EntityMapper<SemstreDTO, Semstre> {



    default Semstre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Semstre semstre = new Semstre();
        semstre.setId(id);
        return semstre;
    }
}
