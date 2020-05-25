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

import com.pfe.gestionnote.domain.Specialite;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.SpecialiteRepository;
import com.pfe.gestionnote.service.dto.SpecialiteCriteria;
import com.pfe.gestionnote.service.dto.SpecialiteDTO;
import com.pfe.gestionnote.service.mapper.SpecialiteMapper;

/**
 * Service for executing complex queries for {@link Specialite} entities in the database.
 * The main input is a {@link SpecialiteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpecialiteDTO} or a {@link Page} of {@link SpecialiteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpecialiteQueryService extends QueryService<Specialite> {

    private final Logger log = LoggerFactory.getLogger(SpecialiteQueryService.class);

    private final SpecialiteRepository specialiteRepository;

    private final SpecialiteMapper specialiteMapper;

    public SpecialiteQueryService(SpecialiteRepository specialiteRepository, SpecialiteMapper specialiteMapper) {
        this.specialiteRepository = specialiteRepository;
        this.specialiteMapper = specialiteMapper;
    }

    /**
     * Return a {@link List} of {@link SpecialiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpecialiteDTO> findByCriteria(SpecialiteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Specialite> specification = createSpecification(criteria);
        return specialiteMapper.toDto(specialiteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpecialiteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpecialiteDTO> findByCriteria(SpecialiteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Specialite> specification = createSpecification(criteria);
        return specialiteRepository.findAll(specification, page)
            .map(specialiteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpecialiteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Specialite> specification = createSpecification(criteria);
        return specialiteRepository.count(specification);
    }

    /**
     * Function to convert {@link SpecialiteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Specialite> createSpecification(SpecialiteCriteria criteria) {
        Specification<Specialite> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Specialite_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Specialite_.libelle));
            }
        }
        return specification;
    }
}
