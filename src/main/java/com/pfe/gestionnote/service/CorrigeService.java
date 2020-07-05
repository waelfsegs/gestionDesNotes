package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.CorrigeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Corrige}.
 */
public interface CorrigeService {

    /**
     * Save a corrige.
     *
     * @param corrigeDTO the entity to save.
     * @return the persisted entity.
     */
    CorrigeDTO save(CorrigeDTO corrigeDTO);

    /**
     * Get all the corriges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CorrigeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" corrige.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CorrigeDTO> findOne(Long id);

    /**
     * Delete the "id" corrige.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
