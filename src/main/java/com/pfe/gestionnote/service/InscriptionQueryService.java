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

import com.pfe.gestionnote.domain.Inscription;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.InscriptionRepository;
import com.pfe.gestionnote.service.dto.InscriptionCriteria;
import com.pfe.gestionnote.service.dto.InscriptionDTO;
import com.pfe.gestionnote.service.mapper.InscriptionMapper;

/**
 * Service for executing complex queries for {@link Inscription} entities in the database.
 * The main input is a {@link InscriptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InscriptionDTO} or a {@link Page} of {@link InscriptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InscriptionQueryService extends QueryService<Inscription> {

    private final Logger log = LoggerFactory.getLogger(InscriptionQueryService.class);

    private final InscriptionRepository inscriptionRepository;

    private final InscriptionMapper inscriptionMapper;

    public InscriptionQueryService(InscriptionRepository inscriptionRepository, InscriptionMapper inscriptionMapper) {
        this.inscriptionRepository = inscriptionRepository;
        this.inscriptionMapper = inscriptionMapper;
    }

    /**
     * Return a {@link List} of {@link InscriptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InscriptionDTO> findByCriteria(InscriptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Inscription> specification = createSpecification(criteria);
        return inscriptionMapper.toDto(inscriptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InscriptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InscriptionDTO> findByCriteria(InscriptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Inscription> specification = createSpecification(criteria);
        return inscriptionRepository.findAll(specification, page)
            .map(inscriptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InscriptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Inscription> specification = createSpecification(criteria);
        return inscriptionRepository.count(specification);
    }

    /**
     * Function to convert {@link InscriptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Inscription> createSpecification(InscriptionCriteria criteria) {
        Specification<Inscription> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Inscription_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Inscription_.date));
            }
            if (criteria.getAnnee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnnee(), Inscription_.annee));
            }
            if (criteria.getEtudiantId() != null) {
                specification = specification.and(buildSpecification(criteria.getEtudiantId(),
                    root -> root.join(Inscription_.etudiant, JoinType.LEFT).get(Etudiant_.id)));
            }
            if (criteria.getClasseId() != null) {
                specification = specification.and(buildSpecification(criteria.getClasseId(),
                    root -> root.join(Inscription_.classe, JoinType.LEFT).get(Classe_.id)));
            }
            if (criteria.getGroupeId() != null) {
                specification = specification.and(buildSpecification(criteria.getGroupeId(),
                    root -> root.join(Inscription_.groupe, JoinType.LEFT).get(Groupe_.id)));
            }
            if (criteria.getSemstreId() != null) {
                specification = specification.and(buildSpecification(criteria.getSemstreId(),
                    root -> root.join(Inscription_.semstre, JoinType.LEFT).get(Semstre_.id)));
            }
            if (criteria.getCycleId() != null) {
                specification = specification.and(buildSpecification(criteria.getCycleId(),
                    root -> root.join(Inscription_.cycle, JoinType.LEFT).get(Cycle_.id)));
            }
            if (criteria.getNiveauId() != null) {
                specification = specification.and(buildSpecification(criteria.getNiveauId(),
                    root -> root.join(Inscription_.niveau, JoinType.LEFT).get(Niveau_.id)));
            }
            if (criteria.getSpecialiteId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialiteId(),
                    root -> root.join(Inscription_.specialite, JoinType.LEFT).get(Specialite_.id)));
            }
        }
        return specification;
    }
}
