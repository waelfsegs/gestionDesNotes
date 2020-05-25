package com.pfe.gestionnote.service.mapper;


import com.pfe.gestionnote.domain.*;
import com.pfe.gestionnote.service.dto.ClasseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classe} and its DTO {@link ClasseDTO}.
 */
@Mapper(componentModel = "spring", uses = {SpecialiteMapper.class, NiveauMapper.class})
public interface ClasseMapper extends EntityMapper<ClasseDTO, Classe> {

    @Mapping(source = "specialite.id", target = "specialiteId")
    @Mapping(source = "niveau.id", target = "niveauId")
    ClasseDTO toDto(Classe classe);

    @Mapping(source = "specialiteId", target = "specialite")
    @Mapping(source = "niveauId", target = "niveau")
    Classe toEntity(ClasseDTO classeDTO);

    default Classe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Classe classe = new Classe();
        classe.setId(id);
        return classe;
    }
}
