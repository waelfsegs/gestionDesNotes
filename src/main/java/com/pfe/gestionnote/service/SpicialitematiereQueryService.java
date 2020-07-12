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

import com.pfe.gestionnote.domain.Spicialitematiere;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.SpicialitematiereRepository;
import com.pfe.gestionnote.service.dto.SpicialitematiereCriteria;
import com.pfe.gestionnote.service.dto.SpicialitematiereDTO;
import com.pfe.gestionnote.service.mapper.SpicialitematiereMapper;

/**
 * Service for executing complex queries for {@link Spicialitematiere} entities in the database.
 * The main input is a {@link SpicialitematiereCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SpicialitematiereDTO} or a {@link Page} of {@link SpicialitematiereDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SpicialitematiereQueryService extends QueryService<Spicialitematiere> {

    private final Logger log = LoggerFactory.getLogger(SpicialitematiereQueryService.class);

    private final SpicialitematiereRepository spicialitematiereRepository;

    private final SpicialitematiereMapper spicialitematiereMapper;

    public SpicialitematiereQueryService(SpicialitematiereRepository spicialitematiereRepository, SpicialitematiereMapper spicialitematiereMapper) {
        this.spicialitematiereRepository = spicialitematiereRepository;
        this.spicialitematiereMapper = spicialitematiereMapper;
    }

    /**
     * Return a {@link List} of {@link SpicialitematiereDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SpicialitematiereDTO> findByCriteria(SpicialitematiereCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Spicialitematiere> specification = createSpecification(criteria);
        return spicialitematiereMapper.toDto(spicialitematiereRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SpicialitematiereDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SpicialitematiereDTO> findByCriteria(SpicialitematiereCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Spicialitematiere> specification = createSpecification(criteria);
        return spicialitematiereRepository.findAll(specification, page)
            .map(spicialitematiereMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SpicialitematiereCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Spicialitematiere> specification = createSpecification(criteria);
        return spicialitematiereRepository.count(specification);
    }

    /**
     * Function to convert {@link SpicialitematiereCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Spicialitematiere> createSpecification(SpicialitematiereCriteria criteria) {
        Specification<Spicialitematiere> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Spicialitematiere_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Spicialitematiere_.libelle));
            }
            if (criteria.getMatiereId() != null) {
                specification = specification.and(buildSpecification(criteria.getMatiereId(),
                    root -> root.join(Spicialitematiere_.matiere, JoinType.LEFT).get(Matiere_.id)));
            }
            if (criteria.getSpecialiteId() != null) {
                specification = specification.and(buildSpecification(criteria.getSpecialiteId(),
                    root -> root.join(Spicialitematiere_.specialite, JoinType.LEFT).get(Specialite_.id)));
            }
        }
        return specification;
    }
}
