package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.RegimeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Regime}.
 */
public interface RegimeService {

    /**
     * Save a regime.
     *
     * @param regimeDTO the entity to save.
     * @return the persisted entity.
     */
    RegimeDTO save(RegimeDTO regimeDTO);

    /**
     * Get all the regimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegimeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" regime.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegimeDTO> findOne(Long id);

    /**
     * Delete the "id" regime.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
