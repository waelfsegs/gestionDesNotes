package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.GroupeService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.GroupeDTO;
import com.pfe.gestionnote.service.dto.GroupeCriteria;
import com.pfe.gestionnote.service.GroupeQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.pfe.gestionnote.domain.Groupe}.
 */
@RestController
@RequestMapping("/api")
public class GroupeResource {

    private final Logger log = LoggerFactory.getLogger(GroupeResource.class);

    private static final String ENTITY_NAME = "groupe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupeService groupeService;

    private final GroupeQueryService groupeQueryService;

    public GroupeResource(GroupeService groupeService, GroupeQueryService groupeQueryService) {
        this.groupeService = groupeService;
        this.groupeQueryService = groupeQueryService;
    }

    /**
     * {@code POST  /groupes} : Create a new groupe.
     *
     * @param groupeDTO the groupeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupeDTO, or with status {@code 400 (Bad Request)} if the groupe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/groupes")
    public ResponseEntity<GroupeDTO> createGroupe(@RequestBody GroupeDTO groupeDTO) throws URISyntaxException {
        log.debug("REST request to save Groupe : {}", groupeDTO);
        if (groupeDTO.getId() != null) {
            throw new BadRequestAlertException("A new groupe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupeDTO result = groupeService.save(groupeDTO);
        return ResponseEntity.created(new URI("/api/groupes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /groupes} : Updates an existing groupe.
     *
     * @param groupeDTO the groupeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupeDTO,
     * or with status {@code 400 (Bad Request)} if the groupeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/groupes")
    public ResponseEntity<GroupeDTO> updateGroupe(@RequestBody GroupeDTO groupeDTO) throws URISyntaxException {
        log.debug("REST request to update Groupe : {}", groupeDTO);
        if (groupeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupeDTO result = groupeService.save(groupeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /groupes} : get all the groupes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupes in body.
     */
    @GetMapping("/groupes")
    public ResponseEntity<List<GroupeDTO>> getAllGroupes(GroupeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Groupes by criteria: {}", criteria);
        Page<GroupeDTO> page = groupeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /groupes/count} : count all the groupes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/groupes/count")
    public ResponseEntity<Long> countGroupes(GroupeCriteria criteria) {
        log.debug("REST request to count Groupes by criteria: {}", criteria);
        return ResponseEntity.ok().body(groupeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /groupes/:id} : get the "id" groupe.
     *
     * @param id the id of the groupeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/groupes/{id}")
    public ResponseEntity<GroupeDTO> getGroupe(@PathVariable Long id) {
        log.debug("REST request to get Groupe : {}", id);
        Optional<GroupeDTO> groupeDTO = groupeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupeDTO);
    }

    /**
     * {@code DELETE  /groupes/:id} : delete the "id" groupe.
     *
     * @param id the id of the groupeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/groupes/{id}")
    public ResponseEntity<Void> deleteGroupe(@PathVariable Long id) {
        log.debug("REST request to delete Groupe : {}", id);

        groupeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
