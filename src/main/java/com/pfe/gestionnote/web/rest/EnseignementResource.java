package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.EnseignementService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.EnseignementDTO;
import com.pfe.gestionnote.service.dto.EnseignementCriteria;
import com.pfe.gestionnote.service.EnseignementQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Enseignement}.
 */
@RestController
@RequestMapping("/api")
public class EnseignementResource {

    private final Logger log = LoggerFactory.getLogger(EnseignementResource.class);

    private static final String ENTITY_NAME = "enseignement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnseignementService enseignementService;

    private final EnseignementQueryService enseignementQueryService;

    public EnseignementResource(EnseignementService enseignementService, EnseignementQueryService enseignementQueryService) {
        this.enseignementService = enseignementService;
        this.enseignementQueryService = enseignementQueryService;
    }

    /**
     * {@code POST  /enseignements} : Create a new enseignement.
     *
     * @param enseignementDTO the enseignementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enseignementDTO, or with status {@code 400 (Bad Request)} if the enseignement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enseignements")
    public ResponseEntity<EnseignementDTO> createEnseignement(@RequestBody EnseignementDTO enseignementDTO) throws URISyntaxException {
        log.debug("REST request to save Enseignement : {}", enseignementDTO);
        if (enseignementDTO.getId() != null) {
            throw new BadRequestAlertException("A new enseignement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnseignementDTO result = enseignementService.save(enseignementDTO);
        return ResponseEntity.created(new URI("/api/enseignements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enseignements} : Updates an existing enseignement.
     *
     * @param enseignementDTO the enseignementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enseignementDTO,
     * or with status {@code 400 (Bad Request)} if the enseignementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enseignementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enseignements")
    public ResponseEntity<EnseignementDTO> updateEnseignement(@RequestBody EnseignementDTO enseignementDTO) throws URISyntaxException {
        log.debug("REST request to update Enseignement : {}", enseignementDTO);
        if (enseignementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnseignementDTO result = enseignementService.save(enseignementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enseignementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enseignements} : get all the enseignements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enseignements in body.
     */
    @GetMapping("/enseignements")
    public ResponseEntity<List<EnseignementDTO>> getAllEnseignements(EnseignementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Enseignements by criteria: {}", criteria);
        Page<EnseignementDTO> page = enseignementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enseignements/count} : count all the enseignements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/enseignements/count")
    public ResponseEntity<Long> countEnseignements(EnseignementCriteria criteria) {
        log.debug("REST request to count Enseignements by criteria: {}", criteria);
        return ResponseEntity.ok().body(enseignementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enseignements/:id} : get the "id" enseignement.
     *
     * @param id the id of the enseignementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enseignementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enseignements/{id}")
    public ResponseEntity<EnseignementDTO> getEnseignement(@PathVariable Long id) {
        log.debug("REST request to get Enseignement : {}", id);
        Optional<EnseignementDTO> enseignementDTO = enseignementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enseignementDTO);
    }

    /**
     * {@code DELETE  /enseignements/:id} : delete the "id" enseignement.
     *
     * @param id the id of the enseignementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enseignements/{id}")
    public ResponseEntity<Void> deleteEnseignement(@PathVariable Long id) {
        log.debug("REST request to delete Enseignement : {}", id);

        enseignementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
