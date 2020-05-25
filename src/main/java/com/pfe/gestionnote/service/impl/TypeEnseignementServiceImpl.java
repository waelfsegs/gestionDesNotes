package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.TypeEnseignementService;
import com.pfe.gestionnote.domain.TypeEnseignement;
import com.pfe.gestionnote.repository.TypeEnseignementRepository;
import com.pfe.gestionnote.service.dto.TypeEnseignementDTO;
import com.pfe.gestionnote.service.mapper.TypeEnseignementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeEnseignement}.
 */
@Service
@Transactional
public class TypeEnseignementServiceImpl implements TypeEnseignementService {

    private final Logger log = LoggerFactory.getLogger(TypeEnseignementServiceImpl.class);

    private final TypeEnseignementRepository typeEnseignementRepository;

    private final TypeEnseignementMapper typeEnseignementMapper;

    public TypeEnseignementServiceImpl(TypeEnseignementRepository typeEnseignementRepository, TypeEnseignementMapper typeEnseignementMapper) {
        this.typeEnseignementRepository = typeEnseignementRepository;
        this.typeEnseignementMapper = typeEnseignementMapper;
    }

    /**
     * Save a typeEnseignement.
     *
     * @param typeEnseignementDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TypeEnseignementDTO save(TypeEnseignementDTO typeEnseignementDTO) {
        log.debug("Request to save TypeEnseignement : {}", typeEnseignementDTO);
        TypeEnseignement typeEnseignement = typeEnseignementMapper.toEntity(typeEnseignementDTO);
        typeEnseignement = typeEnseignementRepository.save(typeEnseignement);
        return typeEnseignementMapper.toDto(typeEnseignement);
    }

    /**
     * Get all the typeEnseignements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeEnseignementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeEnseignements");
        return typeEnseignementRepository.findAll(pageable)
            .map(typeEnseignementMapper::toDto);
    }


    /**
     * Get one typeEnseignement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TypeEnseignementDTO> findOne(Long id) {
        log.debug("Request to get TypeEnseignement : {}", id);
        return typeEnseignementRepository.findById(id)
            .map(typeEnseignementMapper::toDto);
    }

    /**
     * Delete the typeEnseignement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeEnseignement : {}", id);

        typeEnseignementRepository.deleteById(id);
    }
}
