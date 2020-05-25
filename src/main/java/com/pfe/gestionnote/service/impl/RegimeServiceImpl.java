package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.RegimeService;
import com.pfe.gestionnote.domain.Regime;
import com.pfe.gestionnote.repository.RegimeRepository;
import com.pfe.gestionnote.service.dto.RegimeDTO;
import com.pfe.gestionnote.service.mapper.RegimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Regime}.
 */
@Service
@Transactional
public class RegimeServiceImpl implements RegimeService {

    private final Logger log = LoggerFactory.getLogger(RegimeServiceImpl.class);

    private final RegimeRepository regimeRepository;

    private final RegimeMapper regimeMapper;

    public RegimeServiceImpl(RegimeRepository regimeRepository, RegimeMapper regimeMapper) {
        this.regimeRepository = regimeRepository;
        this.regimeMapper = regimeMapper;
    }

    /**
     * Save a regime.
     *
     * @param regimeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RegimeDTO save(RegimeDTO regimeDTO) {
        log.debug("Request to save Regime : {}", regimeDTO);
        Regime regime = regimeMapper.toEntity(regimeDTO);
        regime = regimeRepository.save(regime);
        return regimeMapper.toDto(regime);
    }

    /**
     * Get all the regimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RegimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Regimes");
        return regimeRepository.findAll(pageable)
            .map(regimeMapper::toDto);
    }


    /**
     * Get one regime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RegimeDTO> findOne(Long id) {
        log.debug("Request to get Regime : {}", id);
        return regimeRepository.findById(id)
            .map(regimeMapper::toDto);
    }

    /**
     * Delete the regime by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Regime : {}", id);

        regimeRepository.deleteById(id);
    }
}
