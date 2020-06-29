package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.ExamenService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.ExamenDTO;
import com.pfe.gestionnote.service.dto.ExamenCriteria;
import com.pfe.gestionnote.service.ExamenQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Examen}.
 */
@RestController
@RequestMapping("/api")
public class ExamenResource {

    private final Logger log = LoggerFactory.getLogger(ExamenResource.class);

    private static final String ENTITY_NAME = "examen";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExamenService examenService;

    private final ExamenQueryService examenQueryService;

    public ExamenResource(ExamenService examenService, ExamenQueryService examenQueryService) {
        this.examenService = examenService;
        this.examenQueryService = examenQueryService;
    }

    /**
     * {@code POST  /examen} : Create a new examen.
     *
     * @param examenDTO the examenDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new examenDTO, or with status {@code 400 (Bad Request)} if the examen has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/examen")
    public ResponseEntity<ExamenDTO> createExamen(@RequestBody ExamenDTO examenDTO) throws URISyntaxException {
        log.debug("REST request to save Examen : {}", examenDTO);
        if (examenDTO.getId() != null) {
            throw new BadRequestAlertException("A new examen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamenDTO result = examenService.save(examenDTO);
        return ResponseEntity.created(new URI("/api/examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /examen} : Updates an existing examen.
     *
     * @param examenDTO the examenDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated examenDTO,
     * or with status {@code 400 (Bad Request)} if the examenDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the examenDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/examen")
    public ResponseEntity<ExamenDTO> updateExamen(@RequestBody ExamenDTO examenDTO) throws URISyntaxException {
        log.debug("REST request to update Examen : {}", examenDTO);
        if (examenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamenDTO result = examenService.save(examenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, examenDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /examen} : get all the examen.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of examen in body.
     */
    @GetMapping("/examen")
    public ResponseEntity<List<ExamenDTO>> getAllExamen(ExamenCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Examen by criteria: {}", criteria);
        Page<ExamenDTO> page = examenQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /examen/count} : count all the examen.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/examen/count")
    public ResponseEntity<Long> countExamen(ExamenCriteria criteria) {
        log.debug("REST request to count Examen by criteria: {}", criteria);
        return ResponseEntity.ok().body(examenQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /examen/:id} : get the "id" examen.
     *
     * @param id the id of the examenDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the examenDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/examen/{id}")
    public ResponseEntity<ExamenDTO> getExamen(@PathVariable Long id) {
        log.debug("REST request to get Examen : {}", id);
        Optional<ExamenDTO> examenDTO = examenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(examenDTO);
    }

    /**
     * {@code DELETE  /examen/:id} : delete the "id" examen.
     *
     * @param id the id of the examenDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/examen/{id}")
    public ResponseEntity<Void> deleteExamen(@PathVariable Long id) {
        log.debug("REST request to delete Examen : {}", id);
        examenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
