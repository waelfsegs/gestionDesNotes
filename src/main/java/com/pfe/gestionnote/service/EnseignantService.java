package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.EnseignantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Enseignant}.
 */
public interface EnseignantService {

    /**
     * Save a enseignant.
     *
     * @param enseignantDTO the entity to save.
     * @return the persisted entity.
     */
    EnseignantDTO save(EnseignantDTO enseignantDTO);

    /**
     * Get all the enseignants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EnseignantDTO> findAll(Pageable pageable);


    /**
     * Get the "id" enseignant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnseignantDTO> findOne(Long id);

    /**
     * Delete the "id" enseignant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
