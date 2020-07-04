package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.EnveloppeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Enveloppe}.
 */
public interface EnveloppeService {

    /**
     * Save a enveloppe.
     *
     * @param enveloppeDTO the entity to save.
     * @return the persisted entity.
     */
    EnveloppeDTO save(EnveloppeDTO enveloppeDTO);

    /**
     * Get all the enveloppes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EnveloppeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" enveloppe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EnveloppeDTO> findOne(Long id);

    /**
     * Delete the "id" enveloppe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
