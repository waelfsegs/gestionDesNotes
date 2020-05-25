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

import com.pfe.gestionnote.domain.Niveau;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.NiveauRepository;
import com.pfe.gestionnote.service.dto.NiveauCriteria;
import com.pfe.gestionnote.service.dto.NiveauDTO;
import com.pfe.gestionnote.service.mapper.NiveauMapper;

/**
 * Service for executing complex queries for {@link Niveau} entities in the database.
 * The main input is a {@link NiveauCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NiveauDTO} or a {@link Page} of {@link NiveauDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NiveauQueryService extends QueryService<Niveau> {

    private final Logger log = LoggerFactory.getLogger(NiveauQueryService.class);

    private final NiveauRepository niveauRepository;

    private final NiveauMapper niveauMapper;

    public NiveauQueryService(NiveauRepository niveauRepository, NiveauMapper niveauMapper) {
        this.niveauRepository = niveauRepository;
        this.niveauMapper = niveauMapper;
    }

    /**
     * Return a {@link List} of {@link NiveauDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NiveauDTO> findByCriteria(NiveauCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Niveau> specification = createSpecification(criteria);
        return niveauMapper.toDto(niveauRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NiveauDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NiveauDTO> findByCriteria(NiveauCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Niveau> specification = createSpecification(criteria);
        return niveauRepository.findAll(specification, page)
            .map(niveauMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NiveauCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Niveau> specification = createSpecification(criteria);
        return niveauRepository.count(specification);
    }

    /**
     * Function to convert {@link NiveauCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Niveau> createSpecification(NiveauCriteria criteria) {
        Specification<Niveau> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Niveau_.id));
            }
            if (criteria.getNiveau() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNiveau(), Niveau_.niveau));
            }
            if (criteria.getCycleId() != null) {
                specification = specification.and(buildSpecification(criteria.getCycleId(),
                    root -> root.join(Niveau_.cycle, JoinType.LEFT).get(Cycle_.id)));
            }
        }
        return specification;
    }
}
