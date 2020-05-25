package com.pfe.gestionnote.service.impl;

import com.pfe.gestionnote.service.ResultatService;
import com.pfe.gestionnote.domain.Resultat;
import com.pfe.gestionnote.repository.ResultatRepository;
import com.pfe.gestionnote.service.dto.ResultatDTO;
import com.pfe.gestionnote.service.mapper.ResultatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Resultat}.
 */
@Service
@Transactional
public class ResultatServiceImpl implements ResultatService {

    private final Logger log = LoggerFactory.getLogger(ResultatServiceImpl.class);

    private final ResultatRepository resultatRepository;

    private final ResultatMapper resultatMapper;

    public ResultatServiceImpl(ResultatRepository resultatRepository, ResultatMapper resultatMapper) {
        this.resultatRepository = resultatRepository;
        this.resultatMapper = resultatMapper;
    }

    /**
     * Save a resultat.
     *
     * @param resultatDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ResultatDTO save(ResultatDTO resultatDTO) {
        log.debug("Request to save Resultat : {}", resultatDTO);
        Resultat resultat = resultatMapper.toEntity(resultatDTO);
        resultat = resultatRepository.save(resultat);
        return resultatMapper.toDto(resultat);
    }

    /**
     * Get all the resultats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResultatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Resultats");
        return resultatRepository.findAll(pageable)
            .map(resultatMapper::toDto);
    }


    /**
     * Get one resultat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResultatDTO> findOne(Long id) {
        log.debug("Request to get Resultat : {}", id);
        return resultatRepository.findById(id)
            .map(resultatMapper::toDto);
    }

    /**
     * Delete the resultat by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Resultat : {}", id);

        resultatRepository.deleteById(id);
    }
}
