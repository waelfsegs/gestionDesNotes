package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.ExamenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Examen} and its DTO {@link ExamenDTO}.
 */
@Mapper(componentModel = "spring", uses = {MatiereMapper.class, InscriptionMapper.class, SemstreMapper.class, NiveauMapper.class, SpecialiteMapper.class, EnveloppeMapper.class, CycleMapper.class})
public interface ExamenMapper extends EntityMapper<ExamenDTO, Examen> {

    @Mapping(source = "matiere.id", target = "matiereId")
    @Mapping(source = "inscription.id", target = "inscriptionId")
    @Mapping(source = "semstre.id", target = "semstreId")
    @Mapping(source = "niveau.id", target = "niveauId")
    @Mapping(source = "specialite.id", target = "specialiteId")
    @Mapping(source = "enveloppe.id", target = "enveloppeId")
    @Mapping(source = "cycle.id", target = "cycleId")
    ExamenDTO toDto(Examen examen);

    @Mapping(source = "matiereId", target = "matiere")
    @Mapping(source = "inscriptionId", target = "inscription")
    @Mapping(source = "semstreId", target = "semstre")
    @Mapping(source = "niveauId", target = "niveau")
    @Mapping(source = "specialiteId", target = "specialite")
    @Mapping(source = "enveloppeId", target = "enveloppe")
    @Mapping(source = "cycleId", target = "cycle")
    Examen toEntity(ExamenDTO examenDTO);

    default Examen fromId(Long id) {
        if (id == null) {
            return null;
        }
        Examen examen = new Examen();
        examen.setId(id);
        return examen;
    }
}
