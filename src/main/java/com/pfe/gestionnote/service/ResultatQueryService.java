package com.pfe.gestionnote.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.pfe.gestionnote.domain.Resultat;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.ResultatRepository;
import com.pfe.gestionnote.service.dto.ResultatCriteria;
import com.pfe.gestionnote.service.dto.ResultatDTO;
import com.pfe.gestionnote.service.mapper.ResultatMapper;

/**
 * Service for executing complex queries for {@link Resultat} entities in the database.
 * The main input is a {@link ResultatCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResultatDTO} or a {@link Page} of {@link ResultatDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultatQueryService extends QueryService<Resultat> {

    private final Logger log = LoggerFactory.getLogger(ResultatQueryService.class);

    private final ResultatRepository resultatRepository;

    private final ResultatMapper resultatMapper;

    public ResultatQueryService(ResultatRepository resultatRepository, ResultatMapper resultatMapper) {
        this.resultatRepository = resultatRepository;
        this.resultatMapper = resultatMapper;
    }

    /**
     * Return a {@link List} of {@link ResultatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResultatDTO> findByCriteria(ResultatCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Resultat> specification = createSpecification(criteria);
        return resultatMapper.toDto(resultatRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResultatDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultatDTO> findByCriteria(ResultatCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Resultat> specification = createSpecification(criteria);
        return resultatRepository.findAll(specification, page)
            .map(resultatMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultatCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Resultat> specification = createSpecification(criteria);
        return resultatRepository.count(specification);
    }

    /**
     * Function to convert {@link ResultatCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Resultat> createSpecification(ResultatCriteria criteria) {
        Specification<Resultat> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Resultat_.id));
            }
            if (criteria.getNotecc1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotecc1(), Resultat_.notecc1));
            }
            if (criteria.getNotecc2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNotecc2(), Resultat_.notecc2));
            }
            if (criteria.getNoteexmen() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoteexmen(), Resultat_.noteexmen));
            }
            if (criteria.getMatiereId() != null) {
                specification = specification.and(buildSpecification(criteria.getMatiereId(),
                    root -> root.join(Resultat_.matiere, JoinType.LEFT).get(Matiere_.id)));
            }
            if (criteria.getInscriptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getInscriptionId(),
                    root -> root.join(Resultat_.inscription, JoinType.LEFT).get(Inscription_.id)));
            }
        }
        return specification;
    }
}
