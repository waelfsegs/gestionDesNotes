package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.CycleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Cycle}.
 */
public interface CycleService {

    /**
     * Save a cycle.
     *
     * @param cycleDTO the entity to save.
     * @return the persisted entity.
     */
    CycleDTO save(CycleDTO cycleDTO);

    /**
     * Get all the cycles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CycleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cycle.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CycleDTO> findOne(Long id);

    /**
     * Delete the "id" cycle.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
