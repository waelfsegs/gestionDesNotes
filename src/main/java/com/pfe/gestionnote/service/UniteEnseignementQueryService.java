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

import com.pfe.gestionnote.domain.UniteEnseignement;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.UniteEnseignementRepository;
import com.pfe.gestionnote.service.dto.UniteEnseignementCriteria;
import com.pfe.gestionnote.service.dto.UniteEnseignementDTO;
import com.pfe.gestionnote.service.mapper.UniteEnseignementMapper;

/**
 * Service for executing complex queries for {@link UniteEnseignement} entities in the database.
 * The main input is a {@link UniteEnseignementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UniteEnseignementDTO} or a {@link Page} of {@link UniteEnseignementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UniteEnseignementQueryService extends QueryService<UniteEnseignement> {

    private final Logger log = LoggerFactory.getLogger(UniteEnseignementQueryService.class);

    private final UniteEnseignementRepository uniteEnseignementRepository;

    private final UniteEnseignementMapper uniteEnseignementMapper;

    public UniteEnseignementQueryService(UniteEnseignementRepository uniteEnseignementRepository, UniteEnseignementMapper uniteEnseignementMapper) {
        this.uniteEnseignementRepository = uniteEnseignementRepository;
        this.uniteEnseignementMapper = uniteEnseignementMapper;
    }

    /**
     * Return a {@link List} of {@link UniteEnseignementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UniteEnseignementDTO> findByCriteria(UniteEnseignementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UniteEnseignement> specification = createSpecification(criteria);
        return uniteEnseignementMapper.toDto(uniteEnseignementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UniteEnseignementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UniteEnseignementDTO> findByCriteria(UniteEnseignementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UniteEnseignement> specification = createSpecification(criteria);
        return uniteEnseignementRepository.findAll(specification, page)
            .map(uniteEnseignementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UniteEnseignementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UniteEnseignement> specification = createSpecification(criteria);
        return uniteEnseignementRepository.count(specification);
    }

    /**
     * Function to convert {@link UniteEnseignementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UniteEnseignement> createSpecification(UniteEnseignementCriteria criteria) {
        Specification<UniteEnseignement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UniteEnseignement_.id));
            }
            if (criteria.getNomUE() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomUE(), UniteEnseignement_.nomUE));
            }
            if (criteria.getDesgnationUE() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesgnationUE(), UniteEnseignement_.desgnationUE));
            }
            if (criteria.getCoefficientUE() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoefficientUE(), UniteEnseignement_.coefficientUE));
            }
        }
        return specification;
    }
}
