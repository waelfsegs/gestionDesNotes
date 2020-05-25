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

import com.pfe.gestionnote.domain.Groupe;
import com.pfe.gestionnote.domain.*; // for static metamodels
import com.pfe.gestionnote.repository.GroupeRepository;
import com.pfe.gestionnote.service.dto.GroupeCriteria;
import com.pfe.gestionnote.service.dto.GroupeDTO;
import com.pfe.gestionnote.service.mapper.GroupeMapper;

/**
 * Service for executing complex queries for {@link Groupe} entities in the database.
 * The main input is a {@link GroupeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link GroupeDTO} or a {@link Page} of {@link GroupeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GroupeQueryService extends QueryService<Groupe> {

    private final Logger log = LoggerFactory.getLogger(GroupeQueryService.class);

    private final GroupeRepository groupeRepository;

    private final GroupeMapper groupeMapper;

    public GroupeQueryService(GroupeRepository groupeRepository, GroupeMapper groupeMapper) {
        this.groupeRepository = groupeRepository;
        this.groupeMapper = groupeMapper;
    }

    /**
     * Return a {@link List} of {@link GroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GroupeDTO> findByCriteria(GroupeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Groupe> specification = createSpecification(criteria);
        return groupeMapper.toDto(groupeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GroupeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupeDTO> findByCriteria(GroupeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Groupe> specification = createSpecification(criteria);
        return groupeRepository.findAll(specification, page)
            .map(groupeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GroupeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Groupe> specification = createSpecification(criteria);
        return groupeRepository.count(specification);
    }

    /**
     * Function to convert {@link GroupeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Groupe> createSpecification(GroupeCriteria criteria) {
        Specification<Groupe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Groupe_.id));
            }
            if (criteria.getNomgroup() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomgroup(), Groupe_.nomgroup));
            }
        }
        return specification;
    }
}
