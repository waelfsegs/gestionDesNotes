package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.SpecialiteService;
import com.pfe.gestionnote.domain.Specialite;
import com.pfe.gestionnote.repository.SpecialiteRepository;
import com.pfe.gestionnote.service.dto.SpecialiteDTO;
import com.pfe.gestionnote.service.mapper.SpecialiteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Specialite}.
 */
@Service
@Transactional
public class SpecialiteServiceImpl implements SpecialiteService {

    private final Logger log = LoggerFactory.getLogger(SpecialiteServiceImpl.class);

    private final SpecialiteRepository specialiteRepository;

    private final SpecialiteMapper specialiteMapper;

    public SpecialiteServiceImpl(SpecialiteRepository specialiteRepository, SpecialiteMapper specialiteMapper) {
        this.specialiteRepository = specialiteRepository;
        this.specialiteMapper = specialiteMapper;
    }

    /**
     * Save a specialite.
     *
     * @param specialiteDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SpecialiteDTO save(SpecialiteDTO specialiteDTO) {
        log.debug("Request to save Specialite : {}", specialiteDTO);
        Specialite specialite = specialiteMapper.toEntity(specialiteDTO);
        specialite = specialiteRepository.save(specialite);
        return specialiteMapper.toDto(specialite);
    }

    /**
     * Get all the specialites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SpecialiteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Specialites");
        return specialiteRepository.findAll(pageable)
            .map(specialiteMapper::toDto);
    }


    /**
     * Get one specialite by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpecialiteDTO> findOne(Long id) {
        log.debug("Request to get Specialite : {}", id);
        return specialiteRepository.findById(id)
            .map(specialiteMapper::toDto);
    }

    /**
     * Delete the specialite by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Specialite : {}", id);

        specialiteRepository.deleteById(id);
    }
}
