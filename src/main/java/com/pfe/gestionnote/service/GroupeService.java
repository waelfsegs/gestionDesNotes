package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.GroupeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Groupe}.
 */
public interface GroupeService {

    /**
     * Save a groupe.
     *
     * @param groupeDTO the entity to save.
     * @return the persisted entity.
     */
    GroupeDTO save(GroupeDTO groupeDTO);

    /**
     * Get all the groupes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GroupeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" groupe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GroupeDTO> findOne(Long id);

    /**
     * Delete the "id" groupe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
