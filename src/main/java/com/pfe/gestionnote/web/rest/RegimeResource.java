package com.pfe.gestionnote.web.rest;

import com.pfe.gestionnote.service.RegimeService;
import com.pfe.gestionnote.web.rest.errors.BadRequestAlertException;
import com.pfe.gestionnote.service.dto.RegimeDTO;
import com.pfe.gestionnote.service.dto.RegimeCriteria;
import com.pfe.gestionnote.service.RegimeQueryService;

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
 * REST controller for managing {@link com.pfe.gestionnote.domain.Regime}.
 */
@RestController
@RequestMapping("/api")
public class RegimeResource {

    private final Logger log = LoggerFactory.getLogger(RegimeResource.class);

    private static final String ENTITY_NAME = "regime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegimeService regimeService;

    private final RegimeQueryService regimeQueryService;

    public RegimeResource(RegimeService regimeService, RegimeQueryService regimeQueryService) {
        this.regimeService = regimeService;
        this.regimeQueryService = regimeQueryService;
    }

    /**
     * {@code POST  /regimes} : Create a new regime.
     *
     * @param regimeDTO the regimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regimeDTO, or with status {@code 400 (Bad Request)} if the regime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regimes")
    public ResponseEntity<RegimeDTO> createRegime(@RequestBody RegimeDTO regimeDTO) throws URISyntaxException {
        log.debug("REST request to save Regime : {}", regimeDTO);
        if (regimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new regime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegimeDTO result = regimeService.save(regimeDTO);
        return ResponseEntity.created(new URI("/api/regimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regimes} : Updates an existing regime.
     *
     * @param regimeDTO the regimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regimeDTO,
     * or with status {@code 400 (Bad Request)} if the regimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regimes")
    public ResponseEntity<RegimeDTO> updateRegime(@RequestBody RegimeDTO regimeDTO) throws URISyntaxException {
        log.debug("REST request to update Regime : {}", regimeDTO);
        if (regimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RegimeDTO result = regimeService.save(regimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /regimes} : get all the regimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regimes in body.
     */
    @GetMapping("/regimes")
    public ResponseEntity<List<RegimeDTO>> getAllRegimes(RegimeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Regimes by criteria: {}", criteria);
        Page<RegimeDTO> page = regimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /regimes/count} : count all the regimes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/regimes/count")
    public ResponseEntity<Long> countRegimes(RegimeCriteria criteria) {
        log.debug("REST request to count Regimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(regimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /regimes/:id} : get the "id" regime.
     *
     * @param id the id of the regimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regimes/{id}")
    public ResponseEntity<RegimeDTO> getRegime(@PathVariable Long id) {
        log.debug("REST request to get Regime : {}", id);
        Optional<RegimeDTO> regimeDTO = regimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regimeDTO);
    }

    /**
     * {@code DELETE  /regimes/:id} : delete the "id" regime.
     *
     * @param id the id of the regimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regimes/{id}")
    public ResponseEntity<Void> deleteRegime(@PathVariable Long id) {
        log.debug("REST request to delete Regime : {}", id);

        regimeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
