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

import com.pfe.gestionnote.domain.Corrige;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.CorrigeRepository;
import com.pfe.gestionnote.service.dto.CorrigeCriteria;
import com.pfe.gestionnote.service.dto.CorrigeDTO;
import com.pfe.gestionnote.service.mapper.CorrigeMapper;

/**
 * Service for executing complex queries for {@link Corrige} entities in the database.
 * The main input is a {@link CorrigeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CorrigeDTO} or a {@link Page} of {@link CorrigeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CorrigeQueryService extends QueryService<Corrige> {

    private final Logger log = LoggerFactory.getLogger(CorrigeQueryService.class);

    private final CorrigeRepository corrigeRepository;

    private final CorrigeMapper corrigeMapper;

    public CorrigeQueryService(CorrigeRepository corrigeRepository, CorrigeMapper corrigeMapper) {
        this.corrigeRepository = corrigeRepository;
        this.corrigeMapper = corrigeMapper;
    }

    /**
     * Return a {@link List} of {@link CorrigeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CorrigeDTO> findByCriteria(CorrigeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Corrige> specification = createSpecification(criteria);
        return corrigeMapper.toDto(corrigeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CorrigeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CorrigeDTO> findByCriteria(CorrigeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Corrige> specification = createSpecification(criteria);
        return corrigeRepository.findAll(specification, page)
            .map(corrigeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CorrigeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Corrige> specification = createSpecification(criteria);
        return corrigeRepository.count(specification);
    }

    /**
     * Function to convert {@link CorrigeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Corrige> createSpecification(CorrigeCriteria criteria) {
        Specification<Corrige> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Corrige_.id));
            }
            if (criteria.getEnseignantId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnseignantId(),
                    root -> root.join(Corrige_.enseignant, JoinType.LEFT).get(Enseignant_.id)));
            }
            if (criteria.getEnveloppeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnveloppeId(),
                    root -> root.join(Corrige_.enveloppe, JoinType.LEFT).get(Enveloppe_.id)));
            }
        }
        return specification;
    }
}
