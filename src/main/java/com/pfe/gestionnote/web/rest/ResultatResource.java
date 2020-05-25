package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.ResultatService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.ResultatDTO;
import com.pfe.gestionnote.service.dto.ResultatCriteria;
import com.pfe.gestionnote.service.ResultatQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Resultat}.
 */
@RestController
@RequestMapping("/api")
public class ResultatResource {

    private final Logger log = LoggerFactory.getLogger(ResultatResource.class);

    private static final String ENTITY_NAME = "resultat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultatService resultatService;

    private final ResultatQueryService resultatQueryService;

    public ResultatResource(ResultatService resultatService, ResultatQueryService resultatQueryService) {
        this.resultatService = resultatService;
        this.resultatQueryService = resultatQueryService;
    }

    /**
     * {@code POST  /resultats} : Create a new resultat.
     *
     * @param resultatDTO the resultatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultatDTO, or with status {@code 400 (Bad Request)} if the resultat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultats")
    public ResponseEntity<ResultatDTO> createResultat(@RequestBody ResultatDTO resultatDTO) throws URISyntaxException {
        log.debug("REST request to save Resultat : {}", resultatDTO);
        if (resultatDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultatDTO result = resultatService.save(resultatDTO);
        return ResponseEntity.created(new URI("/api/resultats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultats} : Updates an existing resultat.
     *
     * @param resultatDTO the resultatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultatDTO,
     * or with status {@code 400 (Bad Request)} if the resultatDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultatDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultats")
    public ResponseEntity<ResultatDTO> updateResultat(@RequestBody ResultatDTO resultatDTO) throws URISyntaxException {
        log.debug("REST request to update Resultat : {}", resultatDTO);
        if (resultatDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultatDTO result = resultatService.save(resultatDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultatDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultats} : get all the resultats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultats in body.
     */
    @GetMapping("/resultats")
    public ResponseEntity<List<ResultatDTO>> getAllResultats(ResultatCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Resultats by criteria: {}", criteria);
        Page<ResultatDTO> page = resultatQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resultats/count} : count all the resultats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/resultats/count")
    public ResponseEntity<Long> countResultats(ResultatCriteria criteria) {
        log.debug("REST request to count Resultats by criteria: {}", criteria);
        return ResponseEntity.ok().body(resultatQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /resultats/:id} : get the "id" resultat.
     *
     * @param id the id of the resultatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultats/{id}")
    public ResponseEntity<ResultatDTO> getResultat(@PathVariable Long id) {
        log.debug("REST request to get Resultat : {}", id);
        Optional<ResultatDTO> resultatDTO = resultatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultatDTO);
    }

    /**
     * {@code DELETE  /resultats/:id} : delete the "id" resultat.
     *
     * @param id the id of the resultatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultats/{id}")
    public ResponseEntity<Void> deleteResultat(@PathVariable Long id) {
        log.debug("REST request to delete Resultat : {}", id);

        resultatService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
