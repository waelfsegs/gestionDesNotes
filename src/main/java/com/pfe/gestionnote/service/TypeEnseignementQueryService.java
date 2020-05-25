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

import com.pfe.gestionnote.domain.TypeEnseignement;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.TypeEnseignementRepository;
import com.pfe.gestionnote.service.dto.TypeEnseignementCriteria;
import com.pfe.gestionnote.service.dto.TypeEnseignementDTO;
import com.pfe.gestionnote.service.mapper.TypeEnseignementMapper;

/**
 * Service for executing complex queries for {@link TypeEnseignement} entities in the database.
 * The main input is a {@link TypeEnseignementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TypeEnseignementDTO} or a {@link Page} of {@link TypeEnseignementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TypeEnseignementQueryService extends QueryService<TypeEnseignement> {

    private final Logger log = LoggerFactory.getLogger(TypeEnseignementQueryService.class);

    private final TypeEnseignementRepository typeEnseignementRepository;

    private final TypeEnseignementMapper typeEnseignementMapper;

    public TypeEnseignementQueryService(TypeEnseignementRepository typeEnseignementRepository, TypeEnseignementMapper typeEnseignementMapper) {
        this.typeEnseignementRepository = typeEnseignementRepository;
        this.typeEnseignementMapper = typeEnseignementMapper;
    }

    /**
     * Return a {@link List} of {@link TypeEnseignementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TypeEnseignementDTO> findByCriteria(TypeEnseignementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TypeEnseignement> specification = createSpecification(criteria);
        return typeEnseignementMapper.toDto(typeEnseignementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TypeEnseignementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeEnseignementDTO> findByCriteria(TypeEnseignementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TypeEnseignement> specification = createSpecification(criteria);
        return typeEnseignementRepository.findAll(specification, page)
            .map(typeEnseignementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TypeEnseignementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TypeEnseignement> specification = createSpecification(criteria);
        return typeEnseignementRepository.count(specification);
    }

    /**
     * Function to convert {@link TypeEnseignementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TypeEnseignement> createSpecification(TypeEnseignementCriteria criteria) {
        Specification<TypeEnseignement> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TypeEnseignement_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), TypeEnseignement_.type));
            }
        }
        return specification;
    }
}
