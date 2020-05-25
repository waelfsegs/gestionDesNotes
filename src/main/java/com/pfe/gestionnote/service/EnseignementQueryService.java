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

import com.pfe.gestionnote.domain.Enseignement;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.EnseignementRepository;
import com.pfe.gestionnote.service.dto.EnseignementCriteria;
import com.pfe.gestionnote.service.dto.EnseignementDTO;
import com.pfe.gestionnote.service.mapper.EnseignementMapper;

/**
 * Service for executing complex queries for {@link Enseignement} entities in the database.
 * The main input is a {@link EnseignementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnseignementDTO} or a {@link Page} of {@link EnseignementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnseignementQueryService extends QueryService<Enseignement> {

    private final Logger log = LoggerFactory.getLogger(EnseignementQueryService.class);

    private final EnseignementRepository enseignementRepository;

    private final EnseignementMapper enseignementMapper;

    public EnseignementQueryService(EnseignementRepository enseignementRepository, EnseignementMapper enseignementMapper) {
        this.enseignementRepository = enseignementRepository;
        this.enseignementMapper = enseignementMapper;
    }

    /**
     * Return a {@link List} of {@link EnseignementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnseignementDTO> findByCriteria(EnseignementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Enseignement> specification = createSpecification(criteria);
        return enseignementMapper.toDto(enseignementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnseignementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnseignementDTO> findByCriteria(EnseignementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Enseignement> specification = createSpecification(criteria);
        return enseignementRepository.findAll(specification, page)
            .map(enseignementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnseignementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Enseignement> specification = createSpecification(criteria);
        return enseignementRepository.count(specification);
    }

    /**
     * Function to convert {@link EnseignementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Enseignement> createSpecification(EnseignementCriteria criteria) {
        Specification<Enseignement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Enseignement_.id));
            }
            if (criteria.getMatiereId() != null) {
                specification = specification.and(buildSpecification(criteria.getMatiereId(),
                    root -> root.join(Enseignement_.matiere, JoinType.LEFT).get(Matiere_.id)));
            }
            if (criteria.getEnseignantId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnseignantId(),
                    root -> root.join(Enseignement_.enseignant, JoinType.LEFT).get(Enseignant_.id)));
            }
            if (criteria.getGroupeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeId(),
                    root -> root.join(Enseignement_.groupe, JoinType.LEFT).get(Groupe_.id)));
            }
            if (criteria.getTypeEnseignementId() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeEnseignementId(),
                    root -> root.join(Enseignement_.typeEnseignement, JoinType.LEFT).get(TypeEnseignement_.id)));
            }
        }
        return specification;
    }
}
