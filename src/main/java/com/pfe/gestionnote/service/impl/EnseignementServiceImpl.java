package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.EnseignementService;
import com.pfe.gestionnote.domain.Enseignement;
import com.pfe.gestionnote.repository.EnseignementRepository;
import com.pfe.gestionnote.service.dto.EnseignementDTO;
import com.pfe.gestionnote.service.mapper.EnseignementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Enseignement}.
 */
@Service
@Transactional
public class EnseignementServiceImpl implements EnseignementService {

    private final Logger log = LoggerFactory.getLogger(EnseignementServiceImpl.class);

    private final EnseignementRepository enseignementRepository;

    private final EnseignementMapper enseignementMapper;

    public EnseignementServiceImpl(EnseignementRepository enseignementRepository, EnseignementMapper enseignementMapper) {
        this.enseignementRepository = enseignementRepository;
        this.enseignementMapper = enseignementMapper;
    }

    /**
     * Save a enseignement.
     *
     * @param enseignementDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EnseignementDTO save(EnseignementDTO enseignementDTO) {
        log.debug("Request to save Enseignement : {}", enseignementDTO);
        Enseignement enseignement = enseignementMapper.toEntity(enseignementDTO);
        enseignement = enseignementRepository.save(enseignement);
        return enseignementMapper.toDto(enseignement);
    }

    /**
     * Get all the enseignements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnseignementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enseignements");
        return enseignementRepository.findAll(pageable)
            .map(enseignementMapper::toDto);
    }


    /**
     * Get one enseignement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnseignementDTO> findOne(Long id) {
        log.debug("Request to get Enseignement : {}", id);
        return enseignementRepository.findById(id)
            .map(enseignementMapper::toDto);
    }

    /**
     * Delete the enseignement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enseignement : {}", id);

        enseignementRepository.deleteById(id);
    }
}
