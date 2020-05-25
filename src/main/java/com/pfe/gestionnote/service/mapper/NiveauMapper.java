package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.NiveauDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Niveau} and its DTO {@link NiveauDTO}.
 */
@Mapper(componentModel = "spring", uses = {CycleMapper.class})
public interface NiveauMapper extends EntityMapper<NiveauDTO, Niveau> {

    @Mapping(source = "cycle.id", target = "cycleId")
    NiveauDTO toDto(Niveau niveau);

    @Mapping(source = "cycleId", target = "cycle")
    Niveau toEntity(NiveauDTO niveauDTO);

    default Niveau fromId(Long id) {
        if (id == null) {
            return null;
        }
        Niveau niveau = new Niveau();
        niveau.setId(id);
        return niveau;
    }
}
