package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.EnseignementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Enseignement}.
 */
public interface EnseignementService {

    /**
     * Save a enseignement.
     *
     * @param enseignementDTO the entity to save.
     * @return the persisted entity.
     */
    EnseignementDTO save(EnseignementDTO enseignementDTO);

    /**
     * Get all the enseignements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EnseignementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" enseignement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnseignementDTO> findOne(Long id);

    /**
     * Delete the "id" enseignement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
