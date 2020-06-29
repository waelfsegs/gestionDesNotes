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

import com.pfe.gestionnote.domain.Examen;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.ExamenRepository;
import com.pfe.gestionnote.service.dto.ExamenCriteria;
import com.pfe.gestionnote.service.dto.ExamenDTO;
import com.pfe.gestionnote.service.mapper.ExamenMapper;

/**
 * Service for executing complex queries for {@link Examen} entities in the database.
 * The main input is a {@link ExamenCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExamenDTO} or a {@link Page} of {@link ExamenDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExamenQueryService extends QueryService<Examen> {

    private final Logger log = LoggerFactory.getLogger(ExamenQueryService.class);

    private final ExamenRepository examenRepository;

    private final ExamenMapper examenMapper;

    public ExamenQueryService(ExamenRepository examenRepository, ExamenMapper examenMapper) {
        this.examenRepository = examenRepository;
        this.examenMapper = examenMapper;
    }

    /**
     * Return a {@link List} of {@link ExamenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExamenDTO> findByCriteria(ExamenCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Examen> specification = createSpecification(criteria);
        return examenMapper.toDto(examenRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExamenDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExamenDTO> findByCriteria(ExamenCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Examen> specification = createSpecification(criteria);
        return examenRepository.findAll(specification, page)
            .map(examenMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExamenCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Examen> specification = createSpecification(criteria);
        return examenRepository.count(specification);
    }

    /**
     * Function to convert {@link ExamenCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Examen> createSpecification(ExamenCriteria criteria) {
        Specification<Examen> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Examen_.id));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Examen_.matricule));
            }
            if (criteria.getSession() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSession(), Examen_.session));
            }
            if (criteria.getNumcomp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumcomp(), Examen_.numcomp));
            }
            if (criteria.getMatiereId() != null) {
                specification = specification.and(buildSpecification(criteria.getMatiereId(),
                    root -> root.join(Examen_.matiere, JoinType.LEFT).get(Matiere_.id)));
            }
            if (criteria.getInscriptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getInscriptionId(),
                    root -> root.join(Examen_.inscription, JoinType.LEFT).get(Inscription_.id)));
            }
            if (criteria.getSemstreId() != null) {
                specification = specification.and(buildSpecification(criteria.getSemstreId(),
                    root -> root.join(Examen_.semstre, JoinType.LEFT).get(Semstre_.id)));
            }
            if (criteria.getNiveauId() != null) {
                specification = specification.and(buildSpecification(criteria.getNiveauId(),
                    root -> root.join(Examen_.niveau, JoinType.LEFT).get(Niveau_.id)));
            }
            if (criteria.getSpecialiteId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialiteId(),
                    root -> root.join(Examen_.specialite, JoinType.LEFT).get(Specialite_.id)));
            }
            if (criteria.getEnveloppeId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnveloppeId(),
                    root -> root.join(Examen_.enveloppe, JoinType.LEFT).get(Enveloppe_.id)));
            }
            if (criteria.getCycleId() != null) {
                specification = specification.and(buildSpecification(criteria.getCycleId(),
                    root -> root.join(Examen_.cycle, JoinType.LEFT).get(Cycle_.id)));
            }
        }
        return specification;
    }
}
