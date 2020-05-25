package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.AffectationChefService;
import com.pfe.gestionnote.domain.AffectationChef;
import com.pfe.gestionnote.repository.AffectationChefRepository;
import com.pfe.gestionnote.service.dto.AffectationChefDTO;
import com.pfe.gestionnote.service.mapper.AffectationChefMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AffectationChef}.
 */
@Service
@Transactional
public class AffectationChefServiceImpl implements AffectationChefService {

    private final Logger log = LoggerFactory.getLogger(AffectationChefServiceImpl.class);

    private final AffectationChefRepository affectationChefRepository;

    private final AffectationChefMapper affectationChefMapper;

    public AffectationChefServiceImpl(AffectationChefRepository affectationChefRepository, AffectationChefMapper affectationChefMapper) {
        this.affectationChefRepository = affectationChefRepository;
        this.affectationChefMapper = affectationChefMapper;
    }

    /**
     * Save a affectationChef.
     *
     * @param affectationChefDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AffectationChefDTO save(AffectationChefDTO affectationChefDTO) {
        log.debug("Request to save AffectationChef : {}", affectationChefDTO);
        AffectationChef affectationChef = affectationChefMapper.toEntity(affectationChefDTO);
        affectationChef = affectationChefRepository.save(affectationChef);
        return affectationChefMapper.toDto(affectationChef);
    }

    /**
     * Get all the affectationChefs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AffectationChefDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AffectationChefs");
        return affectationChefRepository.findAll(pageable)
            .map(affectationChefMapper::toDto);
    }


    /**
     * Get one affectationChef by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AffectationChefDTO> findOne(Long id) {
        log.debug("Request to get AffectationChef : {}", id);
        return affectationChefRepository.findById(id)
            .map(affectationChefMapper::toDto);
    }

    /**
     * Delete the affectationChef by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AffectationChef : {}", id);

        affectationChefRepository.deleteById(id);
    }
}
