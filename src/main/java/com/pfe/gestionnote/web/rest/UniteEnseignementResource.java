package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.UniteEnseignementService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.UniteEnseignementDTO;
import com.pfe.gestionnote.service.dto.UniteEnseignementCriteria;
import com.pfe.gestionnote.service.UniteEnseignementQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.UniteEnseignement}.
 */
@RestController
@RequestMapping("/api")
public class UniteEnseignementResource {

    private final Logger log = LoggerFactory.getLogger(UniteEnseignementResource.class);

    private static final String ENTITY_NAME = "uniteEnseignement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UniteEnseignementService uniteEnseignementService;

    private final UniteEnseignementQueryService uniteEnseignementQueryService;

    public UniteEnseignementResource(UniteEnseignementService uniteEnseignementService, UniteEnseignementQueryService uniteEnseignementQueryService) {
        this.uniteEnseignementService = uniteEnseignementService;
        this.uniteEnseignementQueryService = uniteEnseignementQueryService;
    }

    /**
     * {@code POST  /unite-enseignements} : Create a new uniteEnseignement.
     *
     * @param uniteEnseignementDTO the uniteEnseignementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new uniteEnseignementDTO, or with status {@code 400 (Bad Request)} if the uniteEnseignement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unite-enseignements")
    public ResponseEntity<UniteEnseignementDTO> createUniteEnseignement(@RequestBody UniteEnseignementDTO uniteEnseignementDTO) throws URISyntaxException {
        log.debug("REST request to save UniteEnseignement : {}", uniteEnseignementDTO);
        if (uniteEnseignementDTO.getId() != null) {
            throw new BadRequestAlertException("A new uniteEnseignement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UniteEnseignementDTO result = uniteEnseignementService.save(uniteEnseignementDTO);
        return ResponseEntity.created(new URI("/api/unite-enseignements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unite-enseignements} : Updates an existing uniteEnseignement.
     *
     * @param uniteEnseignementDTO the uniteEnseignementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated uniteEnseignementDTO,
     * or with status {@code 400 (Bad Request)} if the uniteEnseignementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the uniteEnseignementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unite-enseignements")
    public ResponseEntity<UniteEnseignementDTO> updateUniteEnseignement(@RequestBody UniteEnseignementDTO uniteEnseignementDTO) throws URISyntaxException {
        log.debug("REST request to update UniteEnseignement : {}", uniteEnseignementDTO);
        if (uniteEnseignementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UniteEnseignementDTO result = uniteEnseignementService.save(uniteEnseignementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, uniteEnseignementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unite-enseignements} : get all the uniteEnseignements.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of uniteEnseignements in body.
     */
    @GetMapping("/unite-enseignements")
    public ResponseEntity<List<UniteEnseignementDTO>> getAllUniteEnseignements(UniteEnseignementCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UniteEnseignements by criteria: {}", criteria);
        Page<UniteEnseignementDTO> page = uniteEnseignementQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /unite-enseignements/count} : count all the uniteEnseignements.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/unite-enseignements/count")
    public ResponseEntity<Long> countUniteEnseignements(UniteEnseignementCriteria criteria) {
        log.debug("REST request to count UniteEnseignements by criteria: {}", criteria);
        return ResponseEntity.ok().body(uniteEnseignementQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /unite-enseignements/:id} : get the "id" uniteEnseignement.
     *
     * @param id the id of the uniteEnseignementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the uniteEnseignementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unite-enseignements/{id}")
    public ResponseEntity<UniteEnseignementDTO> getUniteEnseignement(@PathVariable Long id) {
        log.debug("REST request to get UniteEnseignement : {}", id);
        Optional<UniteEnseignementDTO> uniteEnseignementDTO = uniteEnseignementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(uniteEnseignementDTO);
    }

    /**
     * {@code DELETE  /unite-enseignements/:id} : delete the "id" uniteEnseignement.
     *
     * @param id the id of the uniteEnseignementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unite-enseignements/{id}")
    public ResponseEntity<Void> deleteUniteEnseignement(@PathVariable Long id) {
        log.debug("REST request to delete UniteEnseignement : {}", id);

        uniteEnseignementService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
