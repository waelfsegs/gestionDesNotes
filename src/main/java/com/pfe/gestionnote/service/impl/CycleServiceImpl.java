package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.CycleService;
import com.pfe.gestionnote.domain.Cycle;
import com.pfe.gestionnote.repository.CycleRepository;
import com.pfe.gestionnote.service.dto.CycleDTO;
import com.pfe.gestionnote.service.mapper.CycleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cycle}.
 */
@Service
@Transactional
public class CycleServiceImpl implements CycleService {

    private final Logger log = LoggerFactory.getLogger(CycleServiceImpl.class);

    private final CycleRepository cycleRepository;

    private final CycleMapper cycleMapper;

    public CycleServiceImpl(CycleRepository cycleRepository, CycleMapper cycleMapper) {
        this.cycleRepository = cycleRepository;
        this.cycleMapper = cycleMapper;
    }

    /**
     * Save a cycle.
     *
     * @param cycleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CycleDTO save(CycleDTO cycleDTO) {
        log.debug("Request to save Cycle : {}", cycleDTO);
        Cycle cycle = cycleMapper.toEntity(cycleDTO);
        cycle = cycleRepository.save(cycle);
        return cycleMapper.toDto(cycle);
    }

    /**
     * Get all the cycles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CycleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cycles");
        return cycleRepository.findAll(pageable)
            .map(cycleMapper::toDto);
    }


    /**
     * Get one cycle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CycleDTO> findOne(Long id) {
        log.debug("Request to get Cycle : {}", id);
        return cycleRepository.findById(id)
            .map(cycleMapper::toDto);
    }

    /**
     * Delete the cycle by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cycle : {}", id);

        cycleRepository.deleteById(id);
    }
}
