package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.AffectationChefDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.AffectationChef}.
 */
public interface AffectationChefService {

    /**
     * Save a affectationChef.
     *
     * @param affectationChefDTO the entity to save.
     * @return the persisted entity.
     */
    AffectationChefDTO save(AffectationChefDTO affectationChefDTO);

    /**
     * Get all the affectationChefs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AffectationChefDTO> findAll(Pageable pageable);


    /**
     * Get the "id" affectationChef.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AffectationChefDTO> findOne(Long id);

    /**
     * Delete the "id" affectationChef.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
