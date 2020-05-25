package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.SpecialiteDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Specialite}.
 */
public interface SpecialiteService {

    /**
     * Save a specialite.
     *
     * @param specialiteDTO the entity to save.
     * @return the persisted entity.
     */
    SpecialiteDTO save(SpecialiteDTO specialiteDTO);

    /**
     * Get all the specialites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SpecialiteDTO> findAll(Pageable pageable);


    /**
     * Get the "id" specialite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SpecialiteDTO> findOne(Long id);

    /**
     * Delete the "id" specialite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
