package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.SpicialitematiereDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Spicialitematiere}.
 */
public interface SpicialitematiereService {

    /**
     * Save a spicialitematiere.
     *
     * @param spicialitematiereDTO the entity to save.
     * @return the persisted entity.
     */
    SpicialitematiereDTO save(SpicialitematiereDTO spicialitematiereDTO);

    /**
     * Get all the spicialitematieres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpicialitematiereDTO> findAll(Pageable pageable);

    /**
     * Get the "id" spicialitematiere.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpicialitematiereDTO> findOne(Long id);

    /**
     * Delete the "id" spicialitematiere.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
