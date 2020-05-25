package com.pfe.gestionnote.service;

import com.pfe.gestionnote.service.dto.TypeEnseignementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.pfe.gestionnote.domain.TypeEnseignement}.
 */
public interface TypeEnseignementService {

    /**
     * Save a typeEnseignement.
     *
     * @param typeEnseignementDTO the entity to save.
     * @return the persisted entity.
     */
    TypeEnseignementDTO save(TypeEnseignementDTO typeEnseignementDTO);

    /**
     * Get all the typeEnseignements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeEnseignementDTO> findAll(Pageable pageable);


    /**
     * Get the "id" typeEnseignement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeEnseignementDTO> findOne(Long id);

    /**
     * Delete the "id" typeEnseignement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
