package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.EnveloppeService;
import com.pfe.gestionnote.domain.Enveloppe;
import com.pfe.gestionnote.repository.EnveloppeRepository;
import com.pfe.gestionnote.service.dto.EnveloppeDTO;
import com.pfe.gestionnote.service.mapper.EnveloppeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Enveloppe}.
 */
@Service
@Transactional
public class EnveloppeServiceImpl implements EnveloppeService {

    private final Logger log = LoggerFactory.getLogger(EnveloppeServiceImpl.class);

    private final EnveloppeRepository enveloppeRepository;

    private final EnveloppeMapper enveloppeMapper;

    public EnveloppeServiceImpl(EnveloppeRepository enveloppeRepository, EnveloppeMapper enveloppeMapper) {
        this.enveloppeRepository = enveloppeRepository;
        this.enveloppeMapper = enveloppeMapper;
    }

    /**
     * Save a enveloppe.
     *
     * @param enveloppeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EnveloppeDTO save(EnveloppeDTO enveloppeDTO) {
        log.debug("Request to save Enveloppe : {}", enveloppeDTO);
        Enveloppe enveloppe = enveloppeMapper.toEntity(enveloppeDTO);
        enveloppe = enveloppeRepository.save(enveloppe);
        return enveloppeMapper.toDto(enveloppe);
    }

    /**
     * Get all the enveloppes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnveloppeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enveloppes");
        return enveloppeRepository.findAll(pageable)
            .map(enveloppeMapper::toDto);
    }

    /**
     * Get one enveloppe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnveloppeDTO> findOne(Long id) {
        log.debug("Request to get Enveloppe : {}", id);
        return enveloppeRepository.findById(id)
            .map(enveloppeMapper::toDto);
    }

    /**
     * Delete the enveloppe by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enveloppe : {}", id);
        enveloppeRepository.deleteById(id);
    }
}
