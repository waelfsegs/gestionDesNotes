package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.CycleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cycle} and its DTO {@link CycleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CycleMapper extends EntityMapper<CycleDTO, Cycle> {



    default Cycle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cycle cycle = new Cycle();
        cycle.setId(id);
        return cycle;
    }
}
