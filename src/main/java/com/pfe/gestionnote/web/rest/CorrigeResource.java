package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.CorrigeService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.CorrigeDTO;
import com.pfe.gestionnote.service.dto.CorrigeCriteria;
import com.pfe.gestionnote.service.CorrigeQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Corrige}.
 */
@RestController
@RequestMapping("/api")
public class CorrigeResource {

    private final Logger log = LoggerFactory.getLogger(CorrigeResource.class);

    private static final String ENTITY_NAME = "corrige";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CorrigeService corrigeService;

    private final CorrigeQueryService corrigeQueryService;

    public CorrigeResource(CorrigeService corrigeService, CorrigeQueryService corrigeQueryService) {
        this.corrigeService = corrigeService;
        this.corrigeQueryService = corrigeQueryService;
    }

    /**
     * {@code POST  /corriges} : Create a new corrige.
     *
     * @param corrigeDTO the corrigeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new corrigeDTO, or with status {@code 400 (Bad Request)} if the corrige has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/corriges")
    public ResponseEntity<CorrigeDTO> createCorrige(@RequestBody CorrigeDTO corrigeDTO) throws URISyntaxException {
        log.debug("REST request to save Corrige : {}", corrigeDTO);
        if (corrigeDTO.getId() != null) {
            throw new BadRequestAlertException("A new corrige cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CorrigeDTO result = corrigeService.save(corrigeDTO);
        return ResponseEntity.created(new URI("/api/corriges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /corriges} : Updates an existing corrige.
     *
     * @param corrigeDTO the corrigeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated corrigeDTO,
     * or with status {@code 400 (Bad Request)} if the corrigeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the corrigeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/corriges")
    public ResponseEntity<CorrigeDTO> updateCorrige(@RequestBody CorrigeDTO corrigeDTO) throws URISyntaxException {
        log.debug("REST request to update Corrige : {}", corrigeDTO);
        if (corrigeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CorrigeDTO result = corrigeService.save(corrigeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, corrigeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /corriges} : get all the corriges.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of corriges in body.
     */
    @GetMapping("/corriges")
    public ResponseEntity<List<CorrigeDTO>> getAllCorriges(CorrigeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Corriges by criteria: {}", criteria);
        Page<CorrigeDTO> page = corrigeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /corriges/count} : count all the corriges.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/corriges/count")
    public ResponseEntity<Long> countCorriges(CorrigeCriteria criteria) {
        log.debug("REST request to count Corriges by criteria: {}", criteria);
        return ResponseEntity.ok().body(corrigeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /corriges/:id} : get the "id" corrige.
     *
     * @param id the id of the corrigeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the corrigeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/corriges/{id}")
    public ResponseEntity<CorrigeDTO> getCorrige(@PathVariable Long id) {
        log.debug("REST request to get Corrige : {}", id);
        Optional<CorrigeDTO> corrigeDTO = corrigeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(corrigeDTO);
    }

    /**
     * {@code DELETE  /corriges/:id} : delete the "id" corrige.
     *
     * @param id the id of the corrigeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/corriges/{id}")
    public ResponseEntity<Void> deleteCorrige(@PathVariable Long id) {
        log.debug("REST request to delete Corrige : {}", id);
        corrigeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
