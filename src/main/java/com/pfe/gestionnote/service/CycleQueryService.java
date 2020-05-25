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

import com.pfe.gestionnote.domain.Cycle;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.CycleRepository;
import com.pfe.gestionnote.service.dto.CycleCriteria;
import com.pfe.gestionnote.service.dto.CycleDTO;
import com.pfe.gestionnote.service.mapper.CycleMapper;

/**
 * Service for executing complex queries for {@link Cycle} entities in the database.
 * The main input is a {@link CycleCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CycleDTO} or a {@link Page} of {@link CycleDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CycleQueryService extends QueryService<Cycle> {

    private final Logger log = LoggerFactory.getLogger(CycleQueryService.class);

    private final CycleRepository cycleRepository;

    private final CycleMapper cycleMapper;

    public CycleQueryService(CycleRepository cycleRepository, CycleMapper cycleMapper) {
        this.cycleRepository = cycleRepository;
        this.cycleMapper = cycleMapper;
    }

    /**
     * Return a {@link List} of {@link CycleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CycleDTO> findByCriteria(CycleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Cycle> specification = createSpecification(criteria);
        return cycleMapper.toDto(cycleRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CycleDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CycleDTO> findByCriteria(CycleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Cycle> specification = createSpecification(criteria);
        return cycleRepository.findAll(specification, page)
            .map(cycleMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CycleCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Cycle> specification = createSpecification(criteria);
        return cycleRepository.count(specification);
    }

    /**
     * Function to convert {@link CycleCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Cycle> createSpecification(CycleCriteria criteria) {
        Specification<Cycle> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Cycle_.id));
            }
            if (criteria.getNomcycle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomcycle(), Cycle_.nomcycle));
            }
        }
        return specification;
    }
}
