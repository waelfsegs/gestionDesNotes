package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.UniteEnseignementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.UniteEnseignement}.
 */
public interface UniteEnseignementService {

    /**
     * Save a uniteEnseignement.
     *
     * @param uniteEnseignementDTO the entity to save.
     * @return the persisted entity.
     */
    UniteEnseignementDTO save(UniteEnseignementDTO uniteEnseignementDTO);

    /**
     * Get all the uniteEnseignements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UniteEnseignementDTO> findAll(Pageable pageable);


    /**
     * Get the "id" uniteEnseignement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UniteEnseignementDTO> findOne(Long id);

    /**
     * Delete the "id" uniteEnseignement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
