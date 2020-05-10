package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.EtudiantDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.Etudiant}.
 */
public interface EtudiantService {

    /**
     * Save a etudiant.
     *
     * @param etudiantDTO the entity to save.
     * @return the persisted entity.
     */
    EtudiantDTO save(EtudiantDTO etudiantDTO);

    /**
     * Get all the etudiants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EtudiantDTO> findAll(Pageable pageable);

    /**
     * Get the "id" etudiant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtudiantDTO> findOne(Long id);

    /**
     * Delete the "id" etudiant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
