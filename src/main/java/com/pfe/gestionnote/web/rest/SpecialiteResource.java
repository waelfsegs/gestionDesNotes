package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.SpecialiteService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.SpecialiteDTO;
import com.pfe.gestionnote.service.dto.SpecialiteCriteria;
import com.pfe.gestionnote.service.SpecialiteQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Specialite}.
 */
@RestController
@RequestMapping("/api")
public class SpecialiteResource {

    private final Logger log = LoggerFactory.getLogger(SpecialiteResource.class);

    private static final String ENTITY_NAME = "specialite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialiteService specialiteService;

    private final SpecialiteQueryService specialiteQueryService;

    public SpecialiteResource(SpecialiteService specialiteService, SpecialiteQueryService specialiteQueryService) {
        this.specialiteService = specialiteService;
        this.specialiteQueryService = specialiteQueryService;
    }

    /**
     * {@code POST  /specialites} : Create a new specialite.
     *
     * @param specialiteDTO the specialiteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialiteDTO, or with status {@code 400 (Bad Request)} if the specialite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specialites")
    public ResponseEntity<SpecialiteDTO> createSpecialite(@RequestBody SpecialiteDTO specialiteDTO) throws URISyntaxException {
        log.debug("REST request to save Specialite : {}", specialiteDTO);
        if (specialiteDTO.getId() != null) {
            throw new BadRequestAlertException("A new specialite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialiteDTO result = specialiteService.save(specialiteDTO);
        return ResponseEntity.created(new URI("/api/specialites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /specialites} : Updates an existing specialite.
     *
     * @param specialiteDTO the specialiteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialiteDTO,
     * or with status {@code 400 (Bad Request)} if the specialiteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialiteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specialites")
    public ResponseEntity<SpecialiteDTO> updateSpecialite(@RequestBody SpecialiteDTO specialiteDTO) throws URISyntaxException {
        log.debug("REST request to update Specialite : {}", specialiteDTO);
        if (specialiteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SpecialiteDTO result = specialiteService.save(specialiteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, specialiteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /specialites} : get all the specialites.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialites in body.
     */
    @GetMapping("/specialites")
    public ResponseEntity<List<SpecialiteDTO>> getAllSpecialites(SpecialiteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Specialites by criteria: {}", criteria);
        Page<SpecialiteDTO> page = specialiteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specialites/count} : count all the specialites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/specialites/count")
    public ResponseEntity<Long> countSpecialites(SpecialiteCriteria criteria) {
        log.debug("REST request to count Specialites by criteria: {}", criteria);
        return ResponseEntity.ok().body(specialiteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /specialites/:id} : get the "id" specialite.
     *
     * @param id the id of the specialiteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialiteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specialites/{id}")
    public ResponseEntity<SpecialiteDTO> getSpecialite(@PathVariable Long id) {
        log.debug("REST request to get Specialite : {}", id);
        Optional<SpecialiteDTO> specialiteDTO = specialiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialiteDTO);
    }

    /**
     * {@code DELETE  /specialites/:id} : delete the "id" specialite.
     *
     * @param id the id of the specialiteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specialites/{id}")
    public ResponseEntity<Void> deleteSpecialite(@PathVariable Long id) {
        log.debug("REST request to delete Specialite : {}", id);

        specialiteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
