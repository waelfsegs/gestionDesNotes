package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.GroupeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Groupe} and its DTO {@link GroupeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GroupeMapper extends EntityMapper<GroupeDTO, Groupe> {



    default Groupe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Groupe groupe = new Groupe();
        groupe.setId(id);
        return groupe;
    }
}
