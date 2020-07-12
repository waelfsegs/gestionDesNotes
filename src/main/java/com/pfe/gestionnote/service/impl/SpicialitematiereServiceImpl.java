package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.SpicialitematiereService;
import com.pfe.gestionnote.domain.Spicialitematiere;
import com.pfe.gestionnote.repository.SpicialitematiereRepository;
import com.pfe.gestionnote.service.dto.SpicialitematiereDTO;
import com.pfe.gestionnote.service.mapper.SpicialitematiereMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Spicialitematiere}.
 */
@Service
@Transactional
public class SpicialitematiereServiceImpl implements SpicialitematiereService {

    private final Logger log = LoggerFactory.getLogger(SpicialitematiereServiceImpl.class);

    private final SpicialitematiereRepository spicialitematiereRepository;

    private final SpicialitematiereMapper spicialitematiereMapper;

    public SpicialitematiereServiceImpl(SpicialitematiereRepository spicialitematiereRepository, SpicialitematiereMapper spicialitematiereMapper) {
        this.spicialitematiereRepository = spicialitematiereRepository;
        this.spicialitematiereMapper = spicialitematiereMapper;
    }

    /**
     * Save a spicialitematiere.
     *
     * @param spicialitematiereDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SpicialitematiereDTO save(SpicialitematiereDTO spicialitematiereDTO) {
        log.debug("Request to save Spicialitematiere : {}", spicialitematiereDTO);
        Spicialitematiere spicialitematiere = spicialitematiereMapper.toEntity(spicialitematiereDTO);
        spicialitematiere = spicialitematiereRepository.save(spicialitematiere);
        return spicialitematiereMapper.toDto(spicialitematiere);
    }

    /**
     * Get all the spicialitematieres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SpicialitematiereDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Spicialitematieres");
        return spicialitematiereRepository.findAll(pageable)
            .map(spicialitematiereMapper::toDto);
    }

    /**
     * Get one spicialitematiere by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SpicialitematiereDTO> findOne(Long id) {
        log.debug("Request to get Spicialitematiere : {}", id);
        return spicialitematiereRepository.findById(id)
            .map(spicialitematiereMapper::toDto);
    }

    /**
     * Delete the spicialitematiere by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Spicialitematiere : {}", id);
        spicialitematiereRepository.deleteById(id);
    }
}
