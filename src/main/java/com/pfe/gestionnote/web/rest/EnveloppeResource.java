package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.EnveloppeService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.EnveloppeDTO;
import com.pfe.gestionnote.service.dto.EnveloppeCriteria;
import com.pfe.gestionnote.service.EnveloppeQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Enveloppe}.
 */
@RestController
@RequestMapping("/api")
public class EnveloppeResource {

    private final Logger log = LoggerFactory.getLogger(EnveloppeResource.class);

    private static final String ENTITY_NAME = "enveloppe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnveloppeService enveloppeService;

    private final EnveloppeQueryService enveloppeQueryService;

    public EnveloppeResource(EnveloppeService enveloppeService, EnveloppeQueryService enveloppeQueryService) {
        this.enveloppeService = enveloppeService;
        this.enveloppeQueryService = enveloppeQueryService;
    }

    /**
     * {@code POST  /enveloppes} : Create a new enveloppe.
     *
     * @param enveloppeDTO the enveloppeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enveloppeDTO, or with status {@code 400 (Bad Request)} if the enveloppe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enveloppes")
    public ResponseEntity<EnveloppeDTO> createEnveloppe(@RequestBody EnveloppeDTO enveloppeDTO) throws URISyntaxException {
        log.debug("REST request to save Enveloppe : {}", enveloppeDTO);
        if (enveloppeDTO.getId() != null) {
            throw new BadRequestAlertException("A new enveloppe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnveloppeDTO result = enveloppeService.save(enveloppeDTO);
        return ResponseEntity.created(new URI("/api/enveloppes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enveloppes} : Updates an existing enveloppe.
     *
     * @param enveloppeDTO the enveloppeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enveloppeDTO,
     * or with status {@code 400 (Bad Request)} if the enveloppeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enveloppeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enveloppes")
    public ResponseEntity<EnveloppeDTO> updateEnveloppe(@RequestBody EnveloppeDTO enveloppeDTO) throws URISyntaxException {
        log.debug("REST request to update Enveloppe : {}", enveloppeDTO);
        if (enveloppeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnveloppeDTO result = enveloppeService.save(enveloppeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enveloppeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enveloppes} : get all the enveloppes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enveloppes in body.
     */
    @GetMapping("/enveloppes")
    public ResponseEntity<List<EnveloppeDTO>> getAllEnveloppes(EnveloppeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Enveloppes by criteria: {}", criteria);
        Page<EnveloppeDTO> page = enveloppeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enveloppes/count} : count all the enveloppes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/enveloppes/count")
    public ResponseEntity<Long> countEnveloppes(EnveloppeCriteria criteria) {
        log.debug("REST request to count Enveloppes by criteria: {}", criteria);
        return ResponseEntity.ok().body(enveloppeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enveloppes/:id} : get the "id" enveloppe.
     *
     * @param id the id of the enveloppeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enveloppeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enveloppes/{id}")
    public ResponseEntity<EnveloppeDTO> getEnveloppe(@PathVariable Long id) {
        log.debug("REST request to get Enveloppe : {}", id);
        Optional<EnveloppeDTO> enveloppeDTO = enveloppeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enveloppeDTO);
    }

    /**
     * {@code DELETE  /enveloppes/:id} : delete the "id" enveloppe.
     *
     * @param id the id of the enveloppeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enveloppes/{id}")
    public ResponseEntity<Void> deleteEnveloppe(@PathVariable Long id) {
        log.debug("REST request to delete Enveloppe : {}", id);
        enveloppeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
