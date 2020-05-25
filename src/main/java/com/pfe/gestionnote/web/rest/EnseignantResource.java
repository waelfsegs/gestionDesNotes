package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.EnseignantService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.EnseignantDTO;
import com.pfe.gestionnote.service.dto.EnseignantCriteria;
import com.pfe.gestionnote.service.EnseignantQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Enseignant}.
 */
@RestController
@RequestMapping("/api")
public class EnseignantResource {

    private final Logger log = LoggerFactory.getLogger(EnseignantResource.class);

    private static final String ENTITY_NAME = "enseignant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnseignantService enseignantService;

    private final EnseignantQueryService enseignantQueryService;

    public EnseignantResource(EnseignantService enseignantService, EnseignantQueryService enseignantQueryService) {
        this.enseignantService = enseignantService;
        this.enseignantQueryService = enseignantQueryService;
    }

    /**
     * {@code POST  /enseignants} : Create a new enseignant.
     *
     * @param enseignantDTO the enseignantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enseignantDTO, or with status {@code 400 (Bad Request)} if the enseignant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enseignants")
    public ResponseEntity<EnseignantDTO> createEnseignant(@RequestBody EnseignantDTO enseignantDTO) throws URISyntaxException {
        log.debug("REST request to save Enseignant : {}", enseignantDTO);
        if (enseignantDTO.getId() != null) {
            throw new BadRequestAlertException("A new enseignant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnseignantDTO result = enseignantService.save(enseignantDTO);
        return ResponseEntity.created(new URI("/api/enseignants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enseignants} : Updates an existing enseignant.
     *
     * @param enseignantDTO the enseignantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enseignantDTO,
     * or with status {@code 400 (Bad Request)} if the enseignantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enseignantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enseignants")
    public ResponseEntity<EnseignantDTO> updateEnseignant(@RequestBody EnseignantDTO enseignantDTO) throws URISyntaxException {
        log.debug("REST request to update Enseignant : {}", enseignantDTO);
        if (enseignantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnseignantDTO result = enseignantService.save(enseignantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enseignantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enseignants} : get all the enseignants.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enseignants in body.
     */
    @GetMapping("/enseignants")
    public ResponseEntity<List<EnseignantDTO>> getAllEnseignants(EnseignantCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Enseignants by criteria: {}", criteria);
        Page<EnseignantDTO> page = enseignantQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enseignants/count} : count all the enseignants.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/enseignants/count")
    public ResponseEntity<Long> countEnseignants(EnseignantCriteria criteria) {
        log.debug("REST request to count Enseignants by criteria: {}", criteria);
        return ResponseEntity.ok().body(enseignantQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enseignants/:id} : get the "id" enseignant.
     *
     * @param id the id of the enseignantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enseignantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enseignants/{id}")
    public ResponseEntity<EnseignantDTO> getEnseignant(@PathVariable Long id) {
        log.debug("REST request to get Enseignant : {}", id);
        Optional<EnseignantDTO> enseignantDTO = enseignantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enseignantDTO);
    }

    /**
     * {@code DELETE  /enseignants/:id} : delete the "id" enseignant.
     *
     * @param id the id of the enseignantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enseignants/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        log.debug("REST request to delete Enseignant : {}", id);

        enseignantService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
