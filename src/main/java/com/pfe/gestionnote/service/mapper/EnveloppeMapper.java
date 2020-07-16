package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.EnveloppeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enveloppe} and its DTO {@link EnveloppeDTO}.
 */
@Mapper(componentModel = "spring", uses = {MatiereMapper.class})
public interface EnveloppeMapper extends EntityMapper<EnveloppeDTO, Enveloppe> {

    @Mapping(source = "maiere.id", target = "maiereId")
    @Mapping(source = "maiere.nom", target = "maiere")
    EnveloppeDTO toDto(Enveloppe enveloppe);

    @Mapping(source = "maiereId", target = "maiere")
    Enveloppe toEntity(EnveloppeDTO enveloppeDTO);

    default Enveloppe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enveloppe enveloppe = new Enveloppe();
        enveloppe.setId(id);
        return enveloppe;
    }
}
