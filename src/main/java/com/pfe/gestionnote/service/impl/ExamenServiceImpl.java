package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.ExamenService;
import com.pfe.gestionnote.domain.Examen;
import com.pfe.gestionnote.repository.ExamenRepository;
import com.pfe.gestionnote.service.dto.ExamenDTO;
import com.pfe.gestionnote.service.mapper.ExamenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Examen}.
 */
@Service
@Transactional
public class ExamenServiceImpl implements ExamenService {

    private final Logger log = LoggerFactory.getLogger(ExamenServiceImpl.class);

    private final ExamenRepository examenRepository;

    private final ExamenMapper examenMapper;

    public ExamenServiceImpl(ExamenRepository examenRepository, ExamenMapper examenMapper) {
        this.examenRepository = examenRepository;
        this.examenMapper = examenMapper;
    }

    /**
     * Save a examen.
     *
     * @param examenDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ExamenDTO save(ExamenDTO examenDTO) {
        log.debug("Request to save Examen : {}", examenDTO);
        Examen examen = examenMapper.toEntity(examenDTO);
        examen = examenRepository.save(examen);
        return examenMapper.toDto(examen);
    }

    /**
     * Get all the examen.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Examen");
        return examenRepository.findAll(pageable)
            .map(examenMapper::toDto);
    }

    /**
     * Get one examen by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ExamenDTO> findOne(Long id) {
        log.debug("Request to get Examen : {}", id);
        return examenRepository.findById(id)
            .map(examenMapper::toDto);
    }

    /**
     * Delete the examen by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Examen : {}", id);
        examenRepository.deleteById(id);
    }
}
