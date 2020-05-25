package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.NiveauDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Niveau}.
 */
public interface NiveauService {

    /**
     * Save a niveau.
     *
     * @param niveauDTO the entity to save.
     * @return the persisted entity.
     */
    NiveauDTO save(NiveauDTO niveauDTO);

    /**
     * Get all the niveaus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NiveauDTO> findAll(Pageable pageable);


    /**
     * Get the "id" niveau.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NiveauDTO> findOne(Long id);

    /**
     * Delete the "id" niveau.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
