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

import com.pfe.gestionnote.domain.Enveloppe;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.EnveloppeRepository;
import com.pfe.gestionnote.service.dto.EnveloppeCriteria;
import com.pfe.gestionnote.service.dto.EnveloppeDTO;
import com.pfe.gestionnote.service.mapper.EnveloppeMapper;

/**
 * Service for executing complex queries for {@link Enveloppe} entities in the database.
 * The main input is a {@link EnveloppeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnveloppeDTO} or a {@link Page} of {@link EnveloppeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnveloppeQueryService extends QueryService<Enveloppe> {

    private final Logger log = LoggerFactory.getLogger(EnveloppeQueryService.class);

    private final EnveloppeRepository enveloppeRepository;

    private final EnveloppeMapper enveloppeMapper;

    public EnveloppeQueryService(EnveloppeRepository enveloppeRepository, EnveloppeMapper enveloppeMapper) {
        this.enveloppeRepository = enveloppeRepository;
        this.enveloppeMapper = enveloppeMapper;
    }

    /**
     * Return a {@link List} of {@link EnveloppeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnveloppeDTO> findByCriteria(EnveloppeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Enveloppe> specification = createSpecification(criteria);
        return enveloppeMapper.toDto(enveloppeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnveloppeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnveloppeDTO> findByCriteria(EnveloppeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Enveloppe> specification = createSpecification(criteria);
        return enveloppeRepository.findAll(specification, page)
            .map(enveloppeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnveloppeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Enveloppe> specification = createSpecification(criteria);
        return enveloppeRepository.count(specification);
    }

    /**
     * Function to convert {@link EnveloppeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Enveloppe> createSpecification(EnveloppeCriteria criteria) {
        Specification<Enveloppe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Enveloppe_.id));
            }
            if (criteria.getNameenv() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameenv(), Enveloppe_.nameenv));
            }
            if (criteria.getMaiereId() != null) {
                specification = specification.and(buildSpecification(criteria.getMaiereId(),
                    root -> root.join(Enveloppe_.maiere, JoinType.LEFT).get(Matiere_.id)));
            }
        }
        return specification;
    }
}
