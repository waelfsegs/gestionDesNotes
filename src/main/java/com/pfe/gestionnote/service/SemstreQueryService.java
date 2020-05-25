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

import com.pfe.gestionnote.domain.Semstre;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.SemstreRepository;
import com.pfe.gestionnote.service.dto.SemstreCriteria;
import com.pfe.gestionnote.service.dto.SemstreDTO;
import com.pfe.gestionnote.service.mapper.SemstreMapper;

/**
 * Service for executing complex queries for {@link Semstre} entities in the database.
 * The main input is a {@link SemstreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SemstreDTO} or a {@link Page} of {@link SemstreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SemstreQueryService extends QueryService<Semstre> {

    private final Logger log = LoggerFactory.getLogger(SemstreQueryService.class);

    private final SemstreRepository semstreRepository;

    private final SemstreMapper semstreMapper;

    public SemstreQueryService(SemstreRepository semstreRepository, SemstreMapper semstreMapper) {
        this.semstreRepository = semstreRepository;
        this.semstreMapper = semstreMapper;
    }

    /**
     * Return a {@link List} of {@link SemstreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SemstreDTO> findByCriteria(SemstreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Semstre> specification = createSpecification(criteria);
        return semstreMapper.toDto(semstreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SemstreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SemstreDTO> findByCriteria(SemstreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Semstre> specification = createSpecification(criteria);
        return semstreRepository.findAll(specification, page)
            .map(semstreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SemstreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Semstre> specification = createSpecification(criteria);
        return semstreRepository.count(specification);
    }

    /**
     * Function to convert {@link SemstreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Semstre> createSpecification(SemstreCriteria criteria) {
        Specification<Semstre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Semstre_.id));
            }
            if (criteria.getAnnee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnee(), Semstre_.annee));
            }
            if (criteria.getNumSemstre() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumSemstre(), Semstre_.numSemstre));
            }
        }
        return specification;
    }
}
