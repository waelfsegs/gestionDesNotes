package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.SpicialitematiereService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.SpicialitematiereDTO;
import com.pfe.gestionnote.service.dto.SpicialitematiereCriteria;
import com.pfe.gestionnote.service.SpicialitematiereQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Spicialitematiere}.
 */
@RestController
@RequestMapping("/api")
public class SpicialitematiereResource {

    private final Logger log = LoggerFactory.getLogger(SpicialitematiereResource.class);

    private static final String ENTITY_NAME = "spicialitematiere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpicialitematiereService spicialitematiereService;

    private final SpicialitematiereQueryService spicialitematiereQueryService;

    public SpicialitematiereResource(SpicialitematiereService spicialitematiereService, SpicialitematiereQueryService spicialitematiereQueryService) {
        this.spicialitematiereService = spicialitematiereService;
        this.spicialitematiereQueryService = spicialitematiereQueryService;
    }

    /**
     * {@code POST  /spicialitematieres} : Create a new spicialitematiere.
     *
     * @param spicialitematiereDTO the spicialitematiereDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spicialitematiereDTO, or with status {@code 400 (Bad Request)} if the spicialitematiere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spicialitematieres")
    public ResponseEntity<SpicialitematiereDTO> createSpicialitematiere(@RequestBody SpicialitematiereDTO spicialitematiereDTO) throws URISyntaxException {
        log.debug("REST request to save Spicialitematiere : {}", spicialitematiereDTO);
        if (spicialitematiereDTO.getId() != null) {
            throw new BadRequestAlertException("A new spicialitematiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpicialitematiereDTO result = spicialitematiereService.save(spicialitematiereDTO);
        return ResponseEntity.created(new URI("/api/spicialitematieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /spicialitematieres} : Updates an existing spicialitematiere.
     *
     * @param spicialitematiereDTO the spicialitematiereDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spicialitematiereDTO,
     * or with status {@code 400 (Bad Request)} if the spicialitematiereDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spicialitematiereDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spicialitematieres")
    public ResponseEntity<SpicialitematiereDTO> updateSpicialitematiere(@RequestBody SpicialitematiereDTO spicialitematiereDTO) throws URISyntaxException {
        log.debug("REST request to update Spicialitematiere : {}", spicialitematiereDTO);
        if (spicialitematiereDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpicialitematiereDTO result = spicialitematiereService.save(spicialitematiereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spicialitematiereDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /spicialitematieres} : get all the spicialitematieres.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spicialitematieres in body.
     */
    @GetMapping("/spicialitematieres")
    public ResponseEntity<List<SpicialitematiereDTO>> getAllSpicialitematieres(SpicialitematiereCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Spicialitematieres by criteria: {}", criteria);
        Page<SpicialitematiereDTO> page = spicialitematiereQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /spicialitematieres/count} : count all the spicialitematieres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/spicialitematieres/count")
    public ResponseEntity<Long> countSpicialitematieres(SpicialitematiereCriteria criteria) {
        log.debug("REST request to count Spicialitematieres by criteria: {}", criteria);
        return ResponseEntity.ok().body(spicialitematiereQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /spicialitematieres/:id} : get the "id" spicialitematiere.
     *
     * @param id the id of the spicialitematiereDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spicialitematiereDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/spicialitematieres/{id}")
    public ResponseEntity<SpicialitematiereDTO> getSpicialitematiere(@PathVariable Long id) {
        log.debug("REST request to get Spicialitematiere : {}", id);
        Optional<SpicialitematiereDTO> spicialitematiereDTO = spicialitematiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spicialitematiereDTO);
    }

    /**
     * {@code DELETE  /spicialitematieres/:id} : delete the "id" spicialitematiere.
     *
     * @param id the id of the spicialitematiereDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/spicialitematieres/{id}")
    public ResponseEntity<Void> deleteSpicialitematiere(@PathVariable Long id) {
        log.debug("REST request to delete Spicialitematiere : {}", id);
        spicialitematiereService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
