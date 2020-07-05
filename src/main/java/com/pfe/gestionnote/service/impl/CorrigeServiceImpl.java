package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.CorrigeService;
import com.pfe.gestionnote.domain.Corrige;
import com.pfe.gestionnote.repository.CorrigeRepository;
import com.pfe.gestionnote.service.dto.CorrigeDTO;
import com.pfe.gestionnote.service.mapper.CorrigeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Corrige}.
 */
@Service
@Transactional
public class CorrigeServiceImpl implements CorrigeService {

    private final Logger log = LoggerFactory.getLogger(CorrigeServiceImpl.class);

    private final CorrigeRepository corrigeRepository;

    private final CorrigeMapper corrigeMapper;

    public CorrigeServiceImpl(CorrigeRepository corrigeRepository, CorrigeMapper corrigeMapper) {
        this.corrigeRepository = corrigeRepository;
        this.corrigeMapper = corrigeMapper;
    }

    /**
     * Save a corrige.
     *
     * @param corrigeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CorrigeDTO save(CorrigeDTO corrigeDTO) {
        log.debug("Request to save Corrige : {}", corrigeDTO);
        Corrige corrige = corrigeMapper.toEntity(corrigeDTO);
        corrige = corrigeRepository.save(corrige);
        return corrigeMapper.toDto(corrige);
    }

    /**
     * Get all the corriges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CorrigeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Corriges");
        return corrigeRepository.findAll(pageable)
            .map(corrigeMapper::toDto);
    }

    /**
     * Get one corrige by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CorrigeDTO> findOne(Long id) {
        log.debug("Request to get Corrige : {}", id);
        return corrigeRepository.findById(id)
            .map(corrigeMapper::toDto);
    }

    /**
     * Delete the corrige by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Corrige : {}", id);
        corrigeRepository.deleteById(id);
    }
}
