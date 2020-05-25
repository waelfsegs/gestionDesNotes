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

import com.pfe.gestionnote.domain.Regime;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.RegimeRepository;
import com.pfe.gestionnote.service.dto.RegimeCriteria;
import com.pfe.gestionnote.service.dto.RegimeDTO;
import com.pfe.gestionnote.service.mapper.RegimeMapper;

/**
 * Service for executing complex queries for {@link Regime} entities in the database.
 * The main input is a {@link RegimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegimeDTO} or a {@link Page} of {@link RegimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegimeQueryService extends QueryService<Regime> {

    private final Logger log = LoggerFactory.getLogger(RegimeQueryService.class);

    private final RegimeRepository regimeRepository;

    private final RegimeMapper regimeMapper;

    public RegimeQueryService(RegimeRepository regimeRepository, RegimeMapper regimeMapper) {
        this.regimeRepository = regimeRepository;
        this.regimeMapper = regimeMapper;
    }

    /**
     * Return a {@link List} of {@link RegimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegimeDTO> findByCriteria(RegimeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Regime> specification = createSpecification(criteria);
        return regimeMapper.toDto(regimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RegimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegimeDTO> findByCriteria(RegimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Regime> specification = createSpecification(criteria);
        return regimeRepository.findAll(specification, page)
            .map(regimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Regime> specification = createSpecification(criteria);
        return regimeRepository.count(specification);
    }

    /**
     * Function to convert {@link RegimeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Regime> createSpecification(RegimeCriteria criteria) {
        Specification<Regime> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Regime_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Regime_.type));
            }
        }
        return specification;
    }
}
