package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.SemstreService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.SemstreDTO;
import com.pfe.gestionnote.service.dto.SemstreCriteria;
import com.pfe.gestionnote.service.SemstreQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Semstre}.
 */
@RestController
@RequestMapping("/api")
public class SemstreResource {

    private final Logger log = LoggerFactory.getLogger(SemstreResource.class);

    private static final String ENTITY_NAME = "semstre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SemstreService semstreService;

    private final SemstreQueryService semstreQueryService;

    public SemstreResource(SemstreService semstreService, SemstreQueryService semstreQueryService) {
        this.semstreService = semstreService;
        this.semstreQueryService = semstreQueryService;
    }

    /**
     * {@code POST  /semstres} : Create a new semstre.
     *
     * @param semstreDTO the semstreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new semstreDTO, or with status {@code 400 (Bad Request)} if the semstre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/semstres")
    public ResponseEntity<SemstreDTO> createSemstre(@RequestBody SemstreDTO semstreDTO) throws URISyntaxException {
        log.debug("REST request to save Semstre : {}", semstreDTO);
        if (semstreDTO.getId() != null) {
            throw new BadRequestAlertException("A new semstre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SemstreDTO result = semstreService.save(semstreDTO);
        return ResponseEntity.created(new URI("/api/semstres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /semstres} : Updates an existing semstre.
     *
     * @param semstreDTO the semstreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated semstreDTO,
     * or with status {@code 400 (Bad Request)} if the semstreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the semstreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/semstres")
    public ResponseEntity<SemstreDTO> updateSemstre(@RequestBody SemstreDTO semstreDTO) throws URISyntaxException {
        log.debug("REST request to update Semstre : {}", semstreDTO);
        if (semstreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SemstreDTO result = semstreService.save(semstreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, semstreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /semstres} : get all the semstres.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of semstres in body.
     */
    @GetMapping("/semstres")
    public ResponseEntity<List<SemstreDTO>> getAllSemstres(SemstreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Semstres by criteria: {}", criteria);
        Page<SemstreDTO> page = semstreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /semstres/count} : count all the semstres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/semstres/count")
    public ResponseEntity<Long> countSemstres(SemstreCriteria criteria) {
        log.debug("REST request to count Semstres by criteria: {}", criteria);
        return ResponseEntity.ok().body(semstreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /semstres/:id} : get the "id" semstre.
     *
     * @param id the id of the semstreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the semstreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/semstres/{id}")
    public ResponseEntity<SemstreDTO> getSemstre(@PathVariable Long id) {
        log.debug("REST request to get Semstre : {}", id);
        Optional<SemstreDTO> semstreDTO = semstreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(semstreDTO);
    }

    /**
     * {@code DELETE  /semstres/:id} : delete the "id" semstre.
     *
     * @param id the id of the semstreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/semstres/{id}")
    public ResponseEntity<Void> deleteSemstre(@PathVariable Long id) {
        log.debug("REST request to delete Semstre : {}", id);

        semstreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
