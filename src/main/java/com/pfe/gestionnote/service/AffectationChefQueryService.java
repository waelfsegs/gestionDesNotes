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

import com.pfe.gestionnote.domain.AffectationChef;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.AffectationChefRepository;
import com.pfe.gestionnote.service.dto.AffectationChefCriteria;
import com.pfe.gestionnote.service.dto.AffectationChefDTO;
import com.pfe.gestionnote.service.mapper.AffectationChefMapper;

/**
 * Service for executing complex queries for {@link AffectationChef} entities in the database.
 * The main input is a {@link AffectationChefCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AffectationChefDTO} or a {@link Page} of {@link AffectationChefDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AffectationChefQueryService extends QueryService<AffectationChef> {

    private final Logger log = LoggerFactory.getLogger(AffectationChefQueryService.class);

    private final AffectationChefRepository affectationChefRepository;

    private final AffectationChefMapper affectationChefMapper;

    public AffectationChefQueryService(AffectationChefRepository affectationChefRepository, AffectationChefMapper affectationChefMapper) {
        this.affectationChefRepository = affectationChefRepository;
        this.affectationChefMapper = affectationChefMapper;
    }

    /**
     * Return a {@link List} of {@link AffectationChefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AffectationChefDTO> findByCriteria(AffectationChefCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AffectationChef> specification = createSpecification(criteria);
        return affectationChefMapper.toDto(affectationChefRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AffectationChefDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AffectationChefDTO> findByCriteria(AffectationChefCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AffectationChef> specification = createSpecification(criteria);
        return affectationChefRepository.findAll(specification, page)
            .map(affectationChefMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AffectationChefCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AffectationChef> specification = createSpecification(criteria);
        return affectationChefRepository.count(specification);
    }

    /**
     * Function to convert {@link AffectationChefCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AffectationChef> createSpecification(AffectationChefCriteria criteria) {
        Specification<AffectationChef> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AffectationChef_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), AffectationChef_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), AffectationChef_.endDate));
            }
            if (criteria.getDepartementId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartementId(),
                    root -> root.join(AffectationChef_.departement, JoinType.LEFT).get(Departement_.id)));
            }
            if (criteria.getEnseignantId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnseignantId(),
                    root -> root.join(AffectationChef_.enseignant, JoinType.LEFT).get(Enseignant_.id)));
            }
        }
        return specification;
    }
}
