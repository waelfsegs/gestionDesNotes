package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.AffectationChefService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.AffectationChefDTO;
import com.pfe.gestionnote.service.dto.AffectationChefCriteria;
import com.pfe.gestionnote.service.AffectationChefQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.AffectationChef}.
 */
@RestController
@RequestMapping("/api")
public class AffectationChefResource {

    private final Logger log = LoggerFactory.getLogger(AffectationChefResource.class);

    private static final String ENTITY_NAME = "affectationChef";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffectationChefService affectationChefService;

    private final AffectationChefQueryService affectationChefQueryService;

    public AffectationChefResource(AffectationChefService affectationChefService, AffectationChefQueryService affectationChefQueryService) {
        this.affectationChefService = affectationChefService;
        this.affectationChefQueryService = affectationChefQueryService;
    }

    /**
     * {@code POST  /affectation-chefs} : Create a new affectationChef.
     *
     * @param affectationChefDTO the affectationChefDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affectationChefDTO, or with status {@code 400 (Bad Request)} if the affectationChef has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/affectation-chefs")
    public ResponseEntity<AffectationChefDTO> createAffectationChef(@RequestBody AffectationChefDTO affectationChefDTO) throws URISyntaxException {
        log.debug("REST request to save AffectationChef : {}", affectationChefDTO);
        if (affectationChefDTO.getId() != null) {
            throw new BadRequestAlertException("A new affectationChef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AffectationChefDTO result = affectationChefService.save(affectationChefDTO);
        return ResponseEntity.created(new URI("/api/affectation-chefs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /affectation-chefs} : Updates an existing affectationChef.
     *
     * @param affectationChefDTO the affectationChefDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affectationChefDTO,
     * or with status {@code 400 (Bad Request)} if the affectationChefDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affectationChefDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/affectation-chefs")
    public ResponseEntity<AffectationChefDTO> updateAffectationChef(@RequestBody AffectationChefDTO affectationChefDTO) throws URISyntaxException {
        log.debug("REST request to update AffectationChef : {}", affectationChefDTO);
        if (affectationChefDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AffectationChefDTO result = affectationChefService.save(affectationChefDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affectationChefDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /affectation-chefs} : get all the affectationChefs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affectationChefs in body.
     */
    @GetMapping("/affectation-chefs")
    public ResponseEntity<List<AffectationChefDTO>> getAllAffectationChefs(AffectationChefCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AffectationChefs by criteria: {}", criteria);
        Page<AffectationChefDTO> page = affectationChefQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /affectation-chefs/count} : count all the affectationChefs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/affectation-chefs/count")
    public ResponseEntity<Long> countAffectationChefs(AffectationChefCriteria criteria) {
        log.debug("REST request to count AffectationChefs by criteria: {}", criteria);
        return ResponseEntity.ok().body(affectationChefQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /affectation-chefs/:id} : get the "id" affectationChef.
     *
     * @param id the id of the affectationChefDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affectationChefDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/affectation-chefs/{id}")
    public ResponseEntity<AffectationChefDTO> getAffectationChef(@PathVariable Long id) {
        log.debug("REST request to get AffectationChef : {}", id);
        Optional<AffectationChefDTO> affectationChefDTO = affectationChefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(affectationChefDTO);
    }

    /**
     * {@code DELETE  /affectation-chefs/:id} : delete the "id" affectationChef.
     *
     * @param id the id of the affectationChefDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/affectation-chefs/{id}")
    public ResponseEntity<Void> deleteAffectationChef(@PathVariable Long id) {
        log.debug("REST request to delete AffectationChef : {}", id);

        affectationChefService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
