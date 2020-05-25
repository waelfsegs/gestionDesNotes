package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.RegimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Regime} and its DTO {@link RegimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegimeMapper extends EntityMapper<RegimeDTO, Regime> {



    default Regime fromId(Long id) {
        if (id == null) {
            return null;
        }
        Regime regime = new Regime();
        regime.setId(id);
        return regime;
    }
}
