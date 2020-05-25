package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.CycleService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.CycleDTO;
import com.pfe.gestionnote.service.dto.CycleCriteria;
import com.pfe.gestionnote.service.CycleQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Cycle}.
 */
@RestController
@RequestMapping("/api")
public class CycleResource {

    private final Logger log = LoggerFactory.getLogger(CycleResource.class);

    private static final String ENTITY_NAME = "cycle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CycleService cycleService;

    private final CycleQueryService cycleQueryService;

    public CycleResource(CycleService cycleService, CycleQueryService cycleQueryService) {
        this.cycleService = cycleService;
        this.cycleQueryService = cycleQueryService;
    }

    /**
     * {@code POST  /cycles} : Create a new cycle.
     *
     * @param cycleDTO the cycleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cycleDTO, or with status {@code 400 (Bad Request)} if the cycle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cycles")
    public ResponseEntity<CycleDTO> createCycle(@RequestBody CycleDTO cycleDTO) throws URISyntaxException {
        log.debug("REST request to save Cycle : {}", cycleDTO);
        if (cycleDTO.getId() != null) {
            throw new BadRequestAlertException("A new cycle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CycleDTO result = cycleService.save(cycleDTO);
        return ResponseEntity.created(new URI("/api/cycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cycles} : Updates an existing cycle.
     *
     * @param cycleDTO the cycleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cycleDTO,
     * or with status {@code 400 (Bad Request)} if the cycleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cycleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cycles")
    public ResponseEntity<CycleDTO> updateCycle(@RequestBody CycleDTO cycleDTO) throws URISyntaxException {
        log.debug("REST request to update Cycle : {}", cycleDTO);
        if (cycleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CycleDTO result = cycleService.save(cycleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cycleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cycles} : get all the cycles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cycles in body.
     */
    @GetMapping("/cycles")
    public ResponseEntity<List<CycleDTO>> getAllCycles(CycleCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Cycles by criteria: {}", criteria);
        Page<CycleDTO> page = cycleQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cycles/count} : count all the cycles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cycles/count")
    public ResponseEntity<Long> countCycles(CycleCriteria criteria) {
        log.debug("REST request to count Cycles by criteria: {}", criteria);
        return ResponseEntity.ok().body(cycleQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cycles/:id} : get the "id" cycle.
     *
     * @param id the id of the cycleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cycleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cycles/{id}")
    public ResponseEntity<CycleDTO> getCycle(@PathVariable Long id) {
        log.debug("REST request to get Cycle : {}", id);
        Optional<CycleDTO> cycleDTO = cycleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cycleDTO);
    }

    /**
     * {@code DELETE  /cycles/:id} : delete the "id" cycle.
     *
     * @param id the id of the cycleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cycles/{id}")
    public ResponseEntity<Void> deleteCycle(@PathVariable Long id) {
        log.debug("REST request to delete Cycle : {}", id);

        cycleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
