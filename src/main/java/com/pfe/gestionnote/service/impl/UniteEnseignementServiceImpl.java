package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.UniteEnseignementService;
import com.pfe.gestionnote.domain.UniteEnseignement;
import com.pfe.gestionnote.repository.UniteEnseignementRepository;
import com.pfe.gestionnote.service.dto.UniteEnseignementDTO;
import com.pfe.gestionnote.service.mapper.UniteEnseignementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UniteEnseignement}.
 */
@Service
@Transactional
public class UniteEnseignementServiceImpl implements UniteEnseignementService {

    private final Logger log = LoggerFactory.getLogger(UniteEnseignementServiceImpl.class);

    private final UniteEnseignementRepository uniteEnseignementRepository;

    private final UniteEnseignementMapper uniteEnseignementMapper;

    public UniteEnseignementServiceImpl(UniteEnseignementRepository uniteEnseignementRepository, UniteEnseignementMapper uniteEnseignementMapper) {
        this.uniteEnseignementRepository = uniteEnseignementRepository;
        this.uniteEnseignementMapper = uniteEnseignementMapper;
    }

    /**
     * Save a uniteEnseignement.
     *
     * @param uniteEnseignementDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UniteEnseignementDTO save(UniteEnseignementDTO uniteEnseignementDTO) {
        log.debug("Request to save UniteEnseignement : {}", uniteEnseignementDTO);
        UniteEnseignement uniteEnseignement = uniteEnseignementMapper.toEntity(uniteEnseignementDTO);
        uniteEnseignement = uniteEnseignementRepository.save(uniteEnseignement);
        return uniteEnseignementMapper.toDto(uniteEnseignement);
    }

    /**
     * Get all the uniteEnseignements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UniteEnseignementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UniteEnseignements");
        return uniteEnseignementRepository.findAll(pageable)
            .map(uniteEnseignementMapper::toDto);
    }


    /**
     * Get one uniteEnseignement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UniteEnseignementDTO> findOne(Long id) {
        log.debug("Request to get UniteEnseignement : {}", id);
        return uniteEnseignementRepository.findById(id)
            .map(uniteEnseignementMapper::toDto);
    }

    /**
     * Delete the uniteEnseignement by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UniteEnseignement : {}", id);

        uniteEnseignementRepository.deleteById(id);
    }
}
