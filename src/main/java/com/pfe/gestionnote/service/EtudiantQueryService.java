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

import com.pfe.gestionnote.domain.Etudiant;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.EtudiantRepository;
import com.pfe.gestionnote.service.dto.EtudiantCriteria;
import com.pfe.gestionnote.service.dto.EtudiantDTO;
import com.pfe.gestionnote.service.mapper.EtudiantMapper;

/**
 * Service for executing complex queries for {@link Etudiant} entities in the database.
 * The main input is a {@link EtudiantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EtudiantDTO} or a {@link Page} of {@link EtudiantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EtudiantQueryService extends QueryService<Etudiant> {

    private final Logger log = LoggerFactory.getLogger(EtudiantQueryService.class);

    private final EtudiantRepository etudiantRepository;

    private final EtudiantMapper etudiantMapper;

    public EtudiantQueryService(EtudiantRepository etudiantRepository, EtudiantMapper etudiantMapper) {
        this.etudiantRepository = etudiantRepository;
        this.etudiantMapper = etudiantMapper;
    }

    /**
     * Return a {@link List} of {@link EtudiantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EtudiantDTO> findByCriteria(EtudiantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Etudiant> specification = createSpecification(criteria);
        return etudiantMapper.toDto(etudiantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EtudiantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EtudiantDTO> findByCriteria(EtudiantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Etudiant> specification = createSpecification(criteria);
        return etudiantRepository.findAll(specification, page)
            .map(etudiantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EtudiantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Etudiant> specification = createSpecification(criteria);
        return etudiantRepository.count(specification);
    }

    /**
     * Function to convert {@link EtudiantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Etudiant> createSpecification(EtudiantCriteria criteria) {
        Specification<Etudiant> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Etudiant_.id));
            }
            if (criteria.getCin() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCin(), Etudiant_.cin));
            }
            if (criteria.getNom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNom(), Etudiant_.nom));
            }
            if (criteria.getMatricule() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMatricule(), Etudiant_.matricule));
            }
            if (criteria.getPrenom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrenom(), Etudiant_.prenom));
            }
            if (criteria.getTel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTel(), Etudiant_.tel));
            }
            if (criteria.getDateNais() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateNais(), Etudiant_.dateNais));
            }
        }
        return specification;
    }
}
