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

import com.pfe.gestionnote.domain.Matiere;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.MatiereRepository;
import com.pfe.gestionnote.service.dto.MatiereCriteria;
import com.pfe.gestionnote.service.dto.MatiereDTO;
import com.pfe.gestionnote.service.mapper.MatiereMapper;

/**
 * Service for executing complex queries for {@link Matiere} entities in the database.
 * The main input is a {@link MatiereCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MatiereDTO} or a {@link Page} of {@link MatiereDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MatiereQueryService extends QueryService<Matiere> {

    private final Logger log = LoggerFactory.getLogger(MatiereQueryService.class);

    private final MatiereRepository matiereRepository;

    private final MatiereMapper matiereMapper;

    public MatiereQueryService(MatiereRepository matiereRepository, MatiereMapper matiereMapper) {
        this.matiereRepository = matiereRepository;
        this.matiereMapper = matiereMapper;
    }

    /**
     * Return a {@link List} of {@link MatiereDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MatiereDTO> findByCriteria(MatiereCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Matiere> specification = createSpecification(criteria);
        return matiereMapper.toDto(matiereRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MatiereDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MatiereDTO> findByCriteria(MatiereCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Matiere> specification = createSpecification(criteria);
        return matiereRepository.findAll(specification, page)
            .map(matiereMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MatiereCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Matiere> specification = createSpecification(criteria);
        return matiereRepository.count(specification);
    }

    /**
     * Function to convert {@link MatiereCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Matiere> createSpecification(MatiereCriteria criteria) {
        Specification<Matiere> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Matiere_.id));
            }
            if (criteria.getCoefficientMatiere() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoefficientMatiere(), Matiere_.coefficientMatiere));
            }
            if (criteria.getCoefficientTp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoefficientTp(), Matiere_.coefficientTp));
            }
            if (criteria.getCoefficientDc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoefficientDc(), Matiere_.coefficientDc));
            }
            if (criteria.getCoefficientExem() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCoefficientExem(), Matiere_.coefficientExem));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Matiere_.designation));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Matiere_.nom));
            }
            if (criteria.getRegimeId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegimeId(),
                    root -> root.join(Matiere_.regime, JoinType.LEFT).get(Regime_.id)));
            }
        }
        return specification;
    }
}
