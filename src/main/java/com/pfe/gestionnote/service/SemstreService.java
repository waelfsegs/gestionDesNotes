package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.SemstreDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Semstre}.
 */
public interface SemstreService {

    /**
     * Save a semstre.
     *
     * @param semstreDTO the entity to save.
     * @return the persisted entity.
     */
    SemstreDTO save(SemstreDTO semstreDTO);

    /**
     * Get all the semstres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SemstreDTO> findAll(Pageable pageable);


    /**
     * Get the "id" semstre.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SemstreDTO> findOne(Long id);

    /**
     * Delete the "id" semstre.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
