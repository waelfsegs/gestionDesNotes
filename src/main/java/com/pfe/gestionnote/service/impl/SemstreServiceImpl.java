package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.SemstreService;
import com.pfe.gestionnote.domain.Semstre;
import com.pfe.gestionnote.repository.SemstreRepository;
import com.pfe.gestionnote.service.dto.SemstreDTO;
import com.pfe.gestionnote.service.mapper.SemstreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Semstre}.
 */
@Service
@Transactional
public class SemstreServiceImpl implements SemstreService {

    private final Logger log = LoggerFactory.getLogger(SemstreServiceImpl.class);

    private final SemstreRepository semstreRepository;

    private final SemstreMapper semstreMapper;

    public SemstreServiceImpl(SemstreRepository semstreRepository, SemstreMapper semstreMapper) {
        this.semstreRepository = semstreRepository;
        this.semstreMapper = semstreMapper;
    }

    /**
     * Save a semstre.
     *
     * @param semstreDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SemstreDTO save(SemstreDTO semstreDTO) {
        log.debug("Request to save Semstre : {}", semstreDTO);
        Semstre semstre = semstreMapper.toEntity(semstreDTO);
        semstre = semstreRepository.save(semstre);
        return semstreMapper.toDto(semstre);
    }

    /**
     * Get all the semstres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SemstreDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Semstres");
        return semstreRepository.findAll(pageable)
            .map(semstreMapper::toDto);
    }


    /**
     * Get one semstre by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SemstreDTO> findOne(Long id) {
        log.debug("Request to get Semstre : {}", id);
        return semstreRepository.findById(id)
            .map(semstreMapper::toDto);
    }

    /**
     * Delete the semstre by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Semstre : {}", id);

        semstreRepository.deleteById(id);
    }
}
