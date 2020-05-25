package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.ResultatDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Resultat}.
 */
public interface ResultatService {

    /**
     * Save a resultat.
     *
     * @param resultatDTO the entity to save.
     * @return the persisted entity.
     */
    ResultatDTO save(ResultatDTO resultatDTO);

    /**
     * Get all the resultats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResultatDTO> findAll(Pageable pageable);


    /**
     * Get the "id" resultat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResultatDTO> findOne(Long id);

    /**
     * Delete the "id" resultat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
